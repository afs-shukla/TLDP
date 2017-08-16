package models

import javax.inject.Inject


import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile


import scala.concurrent.Future

/**
  * Created by afssh on 09-08-2017.
  */


case class UsersDetail(id:Long, userId:String, userFirstName: String, userLastName:String
                       , userManager:String, userProcessName:String, userTeam:String, userPhone:String, userEmail:String)

class UsersRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._

  private[models] val Users = TableQuery[UsersTable]


  private def _findById(id: Long): DBIO[Option[UsersDetail]] =
    Users.filter(_.id === id).result.headOption

  private def _findByName(name: String): Query[UsersTable, UsersDetail, List] =
    Users.filter(_.userFirstName === name).to[List]

  def findById(id: Long): Future[Option[UsersDetail]] =
    db.run(_findById(id))

  def findByName(name: String): Future[List[UsersDetail]] =
    db.run(_findByName(name).result)
  /*
    def all: Future[List[Project]] =
      db.run(Projects.to[List].result)

    def create(name: String): Future[Long] = {
      val project = Project(0, name)
      db.run(Projects returning Projects.map(_.id) += project)
    }
  */


  def delete(id: Long): Future[Int] = {
    val action = Users.filter(_.id === id).delete

    db.run(action.transactionally)
  }

  def appUserSearch(userId:Option[String],userFistName:Option[String],userLastName:Option[String]):Future[List[UsersDetail]] ={
    val action =  for {
      userlist <- Users.filter(a =>
        userId.map (id =>
          a.userId like  s"%${id}%").getOrElse(slick.lifted.LiteralColumn(true)) &&
          userFistName.map ( firstname =>
            a.userFirstName like s"%${firstname}%").getOrElse(slick.lifted.LiteralColumn(true)) &&
          userLastName.map ( lastname=>
            a.userLastName like s"%${lastname}%").getOrElse(slick.lifted.LiteralColumn(true))
      ).to[List]

    }yield userlist
    db.run(action.result)


  }

  def updateUser(user:UsersDetail)={
    val action= Users.filter (_.id === user.id ).map (c=>
      (c.userId,c.userFirstName,c.userLastName,c.userEmail,c.userPhone,c.userManager,c.userProcessName,c.userTeam))
       .update(user.userId,user.userFirstName,user.userLastName,user.userEmail,user.userPhone,user.userManager,user.userProcessName,user.userTeam)
    db.run(action)

  }




  /*  def addTask(color: String, projectId: Long): Future[Long] = {
      val interaction = for {
        Some(project) <- _findById(projectId)
        id <- taskRepo.insert(Task(0, color, TaskStatus.ready, project.id))
      } yield id

      db.run(interaction.transactionally)
    }*/

  def all: Future[List[UsersDetail]] =
    db.run(Users.to[List].result)

  def create(userId:String,userFirstName: String,userLastName:String
             ,userManager:String,userProcessName:String,userTeam:String,userPhone:String,userEmail:String): Future[Long] = {
    val project = UsersDetail(0, userId,userFirstName,userLastName,userManager,userProcessName,userTeam,userPhone,userEmail)
    db.run(Users returning Users.map(_.id) += project)
  }

  private[models] class UsersTable(tag: Tag) extends Table[UsersDetail](tag, "T_USERS") {
    def id=column[Long]("ID",O.AutoInc,O.PrimaryKey)
    def userId = column[String]("USER_ID")
    def userFirstName = column[String]("USER_FIRST_NAME")
    def userLastName=column[String]("USER_LAST_NAME")
    def userManager=column[String]("USER_MGR_NAME")
    def userProcessName=column[String]("USER_PROC_NAME")
    def userTeam=column[String]("USER_TEAM_NAME")
    def userPhone=column[String]("USER_PHONE")
    def userEmail=column[String]("USER_EMAIL")


    def * = (id, userId,userFirstName,userLastName,userManager,userProcessName,userTeam,userPhone,userEmail) <>(UsersDetail.tupled, UsersDetail.unapply)

    def ? = (id.?, userId.?,userFirstName.?,userLastName.?,userManager.?,userProcessName.?,userTeam.?,userPhone.?,userEmail.?).
      shaped.<>({ r => import r._; _1.map(_ => UsersDetail.tupled((_1.get, _2.get,_3.get,_4.get,_5.get,_6.get,_7.get,_8.get,_9.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }



}

