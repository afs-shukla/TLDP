package models

import javax.inject.Inject


import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile


import scala.concurrent.Future
/**
  * Created by afssh on 13-08-2017.
  */

case class UserApp( userId:Long, appId:Long)
class UserAppRepo  @Inject()(val userRepo:UsersRepo)(val appRepo:AppsRepo)(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._

   val AppUsersRels = TableQuery[AppsUsersRelTable]



  private def _findByAppId(appid: Long): Query[AppsUsersRelTable, UserApp, List] =
    AppUsersRels.filter(_.appId === appid).to[List]

  private def _findByUserId(uid: Long): Query[AppsUsersRelTable, UserApp, List] =
    AppUsersRels.filter(_.userId === uid).to[List]



  def findByAppId(appid: Long): Future[List[UserApp]] =
    db.run(_findByAppId(appid).result)

  def findByUserId(uid: Long): Future[List[UserApp]] =
    db.run(_findByUserId(uid).result)


  def delete(userApp:UserApp): Future[Int] = {
    val action = AppUsersRels.filter(_.userId === userApp.userId).filter(_.appId === userApp.appId).delete

    db.run(action.transactionally)
  }

  def updateUserApp(ua:UserApp)={
    val action= AppUsersRels.filter (_.userId === ua.userId ). filter(_.appId === ua.appId).map (c=>
      (c.userId,c.appId))
      .update(ua.userId,ua.appId)
    db.run(action)

  }
  def all: Future[List[UserApp]] =
    db.run(AppUsersRels.to[List].result)
  def create( userId:Long, appId:Long): Future[Unit] = {
    val action= DBIO.seq(AppUsersRels += UserApp(userId,appId))

    db.run(action)
  }


  def findAllUsersByAppId(appId:Long):Future[Seq[(UsersDetail,UserApp)]] ={
    val action= for {
      au <- AppUsersRels if au.appId === appId
      users <- userRepo.Users  if au.userId === users.id
    }yield (users,au)
    db.run(action.result)
  }

  def findAllAppsByUserId(userId:Long):Future[Seq[(TargetApps,UserApp)]] ={
    val action= for {
      au <- AppUsersRels if au.userId === userId
      apps <- appRepo.Apps  if au.userId === apps.id
    }yield (apps,au)
    db.run(action.result)
  }



 class AppsUsersRelTable(tag: Tag) extends Table[UserApp](tag, "USER_APP") {
    def userId = column[Long]("USERID")
    def appId = column[Long]("APPID")

    def * = (userId,appId) <>(UserApp.tupled, UserApp.unapply)

    def ? = ( userId.?,appId.?).
      shaped.<>({ r => import r._; _1.map(_ => UserApp.tupled(( _1.get,_2.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
    def pk =primaryKey("pk_app_users",(userId,appId))
    def aFK = foreignKey("fk_user_userid", userId, userRepo.Users)(_.id, onDelete = ForeignKeyAction.Cascade)
    def bFK = foreignKey("fk_apps_appid", appId, appRepo.Apps)(_.id, onDelete = ForeignKeyAction.Cascade)
  }

}
