package models

import java.sql.Timestamp
import java.util.Date
import javax.inject.Inject


import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.sql.SqlProfile.ColumnOption.SqlType


import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

/**
  * Created by afssh on 14-08-2017.
  */

case class Deploy(id:Long,depNumber:String, userId:Long, appId: Long, depDate: Timestamp
                       , depEnvironment:String, depStatus:String, depRemarks:String)
class DeployRepo @Inject()(val userRepo:UsersRepo)(val appRepo:AppsRepo)(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._
  private[models] val deployments = TableQuery[DeployTable]



  def findById(id: Long): Future[Option[Deploy]] =
    db.run( deployments.filter(_.id === id).result.headOption)

  def findByUserId(uid: Long): Future[List[Deploy]] =
    db.run((deployments.filter(_.userId === uid).to[List]).result)

  def findByAppId(aid: Long): Future[List[Deploy]] =
    db.run((deployments.filter(_.appId === aid).to[List]).result)


  def findByDate(dd:Timestamp):Future[List[Deploy]]={
    db.run((deployments.filter(_.depDate === dd).to[List]).result)
  }




/*  def getUserDetails(id:Long):Future[Option[UsersDetail]]={
    val deploy= Await.result(findById(id),Duration.Inf)
   val action = deploy match {
      case Some(s) => userRepo.findById(s.userId)
      case None => Future[Option[None]]
    }

  }*/

  def all: Future[List[Deploy]] =
    db.run(deployments.to[List].result)

  def create(depNumber:String,userId:Long,appId: Long, depdate:Timestamp,depEnv:String,depStatus:String,depRemarks:String):Future[Long]={
    val dep = Deploy(0,depNumber, userId,appId,depdate,depEnv,depStatus,depRemarks)
    db.run(deployments returning deployments.map(_.id) += dep)
  }

  private[models] class DeployTable(tag: Tag) extends Table[Deploy](tag, "T_DEPLOY") {
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def depNumber=column[String]("DEP_ID")
    def userId = column[Long]("USER_ID")
    def appId = column[Long]("APP_ID")
    def depDate = column[Timestamp]("DEP_DATE",SqlType("timestamp not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP\""))
    def depEnvironment = column[String]("DEP_ENV")
    def depStatus = column[String]("DEP_STATUS")
    def depRemarks = column[String]("DEP_REMARKS")

    def * = (id, depNumber,userId, appId, depDate, depEnvironment, depStatus, depRemarks) <>(Deploy.tupled, Deploy.unapply)

    def userFK = foreignKey("fk_user_userid", userId, userRepo.Users)(_.id, onDelete = ForeignKeyAction.Cascade)
    def appFK = foreignKey("fk_apps_appid", appId, appRepo.Apps)(_.id, onDelete = ForeignKeyAction.Cascade)

    def ? = (id.?,depNumber.?, userId.?, appId.?, depDate.?, depEnvironment.?, depStatus.?, depRemarks.?).
      shaped.<>({ r => import r._; _1.map(_ => Deploy.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get,_8.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
  }

 /* object DateMapper {

    val utilDate2SqlTimestampMapper = MappedColumnType.base[java.util.Date, java.sql.Timestamp](
      { utilDate => new java.sql.Timestamp(utilDate.getTime()) },
      { sqlTimestamp => new java.util.Date(sqlTimestamp.getTime()) })

    val utilDate2SqlDate = MappedColumnType.base[java.util.Date, java.sql.Date](
      { utilDate => new java.sql.Date(utilDate.getTime()) },
      { sqlDate => new java.util.Date(sqlDate.getTime()) })

  }*/
}


