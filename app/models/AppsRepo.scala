package models

import javax.inject.Inject


import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile


import scala.concurrent.Future

/**
  * Created by afssh on 09-08-2017.
  */


case class App1(id:Long,appId:String,appName: String,appDesc:String,appType:String,appContainer:String,appDepType:String,
                appProjManager:String,appProcessName:String,appTeam:String,appRepository:String,appPlaybook:String)

class AppsRepo @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._

  private[models] val Apps = TableQuery[AppsTable]


  private def _findById(id: Long): DBIO[Option[App1]] =
    Apps.filter(_.id === id).result.headOption

  private def _findByName(name: String): Query[AppsTable, App1, List] =
    Apps.filter(_.appName === name).to[List]

  def findById(id: Long): Future[Option[App1]] =
    db.run(_findById(id))

  def findByName(name: String): Future[List[App1]] =
    db.run(_findByName(name).result)
  /*
    def all: Future[List[Project]] =
      db.run(Projects.to[List].result)

    def create(name: String): Future[Long] = {
      val project = Project(0, name)
      db.run(Projects returning Projects.map(_.id) += project)
    }
  */

/*
  def delete(name: String): Future[Int] = {
    val query = _findByName(name)

    val interaction = for {
      projects <- query.result
      _ <- DBIO.sequence(projects.map(p => taskRepo._deleteAllInProject(p.id)))
      projectsDeleted <- query.delete
    } yield projectsDeleted

    db.run(interaction.transactionally)
  }
*/

/*  def addTask(color: String, projectId: Long): Future[Long] = {
    val interaction = for {
      Some(project) <- _findById(projectId)
      id <- taskRepo.insert(Task(0, color, TaskStatus.ready, project.id))
    } yield id

    db.run(interaction.transactionally)
  }*/

def all: Future[List[App1]] =
    db.run(Apps.to[List].result)
def create(appId: String,appName:String,appDesc:String,appType:String,appContainer:String,appDepType:String,
           appProjManager:String,appProcessName:String,appTeam:String,appRepository:String,appPlaybook:String): Future[Long] = {
  val project = App1(0, appId,appName,appDesc,appType,appContainer,appDepType,appProjManager,appProcessName,appTeam,appRepository,appPlaybook)
  db.run(Apps returning Apps.map(_.id) += project)
}

  private[models] class AppsTable(tag: Tag) extends Table[App1](tag, "T_APP") {
    def id=column[Long]("ID",O.AutoInc,O.PrimaryKey)
    def appId = column[String]("C_APPID")
    def appName = column[String]("C_APPNAME")
    def appDesc=column[String]("C_APPDESC")
    def appType=column[String]("C_APPTYPE")
    def appContainer=column[String]("C_CONTAINER")
    def appDepType=column[String]("C_DEPTYPE")
    def appProjManager=column[String]("C_PROJMGR")
    def appProcessName=column[String]("C_PROCESSNAME")
    def appTeam=column[String]("C_TEAM")
    def appRepository=column[String]("C_APPREPO")
    def appPlaybook=column[String]("C_PLAYBOOK")


    def * = (id, appId,appName,appDesc,appType,appContainer,appDepType,appProjManager,appProcessName,appTeam,appRepository,appPlaybook) <>(App1.tupled, App1.unapply)

    def ? = (id.?, appId.?,appName.?,appDesc.?,appType.?,appContainer.?,appDepType.?,appProjManager.?,appProcessName.?,appTeam.?,appRepository.?,appPlaybook.?).
      shaped.<>({ r => import r._; _1.map(_ => App1.tupled((_1.get, _2.get,_3.get,_4.get,_5.get,_6.get,_7.get,_8.get,_9.get,_10.get,_11.get,_12.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }

}