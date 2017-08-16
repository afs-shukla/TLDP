package models

import javax.inject.Inject


import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile


import scala.concurrent.Future

/**
  * Created by afssh on 09-08-2017.
  */


case class TargetApps(id:Long, appId:String, appName: String, appDesc:String, appType:String, appContainer:String, appDepType:String,
                      appProjManager:String, appProcessName:String, appTeam:String, appRepository:String, appPlaybook:String)

class AppsRepo @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._

  val Apps = TableQuery[AppsTable]


  private def _findById(id: Long): DBIO[Option[TargetApps]] =
    Apps.filter(_.id === id).result.headOption

  private def _findByName(name: String): Query[AppsTable, TargetApps, List] =
    Apps.filter(_.appName === name).to[List]

  def findById(id: Long): Future[Option[TargetApps]] =
    db.run(_findById(id))

  def findByName(name: String): Future[List[TargetApps]] =
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
    val action = Apps.filter(_.id === id).delete

    db.run(action.transactionally)
  }

  def appSearch(appId:Option[String],appName:Option[String],appDesc:Option[String]):Future[List[TargetApps]] ={
    val action =  for {
       applist <- Apps.filter( a =>
                             appId.map (id =>
                             a.appId like  s"%${id}%").getOrElse(slick.lifted.LiteralColumn(true)) &&
                             appName.map ( name =>
                             a.appName like s"%${name}%").getOrElse(slick.lifted.LiteralColumn(true)) &&
                             appDesc.map ( desc=>
                             a.appDesc like "%desc%").getOrElse(slick.lifted.LiteralColumn(true))
      ).to[List]

    }yield applist
    db.run(action.result)


  }

  def updateApp(app:TargetApps)={
    val action= Apps.filter (_.id === app.id ).map (c=>
      (c.appId,c.appName,c.appDesc,c.appType,c.appContainer,c.appPlaybook,c.appDepType,c.appProcessName,
        c.appProjManager,c.appRepository,c.appTeam)).
      update(app.appId,app.appName,app.appDesc,app.appType,app.appContainer,app.appPlaybook,
      app.appDepType,app.appProcessName,app.appProjManager,app.appRepository,app.appTeam)

      db.run(action)

  }



/*  def addTask(color: String, projectId: Long): Future[Long] = {
    val interaction = for {
      Some(project) <- _findById(projectId)
      id <- taskRepo.insert(Task(0, color, TaskStatus.ready, project.id))
    } yield id

    db.run(interaction.transactionally)
  }*/
//( "Action1","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
def all: Future[List[TargetApps]] =
    db.run(Apps.to[List].result)
def create(appId: String,appName:String,appDesc:String,appType:String,appContainer:String,appDepType:String,
           appProjManager:String,appProcessName:String,appTeam:String,appRepository:String,appPlaybook:String): Future[Long] = {
  val project = TargetApps(0, appId,appName,appDesc,appType,appContainer,appDepType,appProjManager,appProcessName,appTeam,appRepository,appPlaybook)
  db.run(Apps returning Apps.map(_.id) += project)
}

  class AppsTable(tag: Tag) extends Table[TargetApps](tag, "T_APP") {
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


    def * = (id, appId,appName,appDesc,appType,appContainer,appDepType,appProjManager,appProcessName,appTeam,appRepository,appPlaybook) <>(TargetApps.tupled, TargetApps.unapply)

    def ? = (id.?, appId.?,appName.?,appDesc.?,appType.?,appContainer.?,appDepType.?,appProjManager.?,appProcessName.?,appTeam.?,appRepository.?,appPlaybook.?).
      shaped.<>({ r => import r._; _1.map(_ => TargetApps.tupled((_1.get, _2.get,_3.get,_4.get,_5.get,_6.get,_7.get,_8.get,_9.get,_10.get,_11.get,_12.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }

}