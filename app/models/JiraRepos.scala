package models

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by afssh on 14-08-2017.
  */


case class JiraDetail(id:Long,depId:Long,jiraNo:String,jiraDesc:String,jiraStatus:String,fixVersion:String)
class JiraRepos @Inject()(val depRepo:DeployRepo)(val userRepo:UsersRepo)(val appRepo:AppsRepo)(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._
  private[models] val jiras = TableQuery[JiraTable]

  def findJiraDetailsByDeplId(depId:Long):Future[List[JiraDetail]]={
    val action = jiras.filter(_.depId===depId).to[List]
    db.run(action.result)

  }


  def findById(id: Long): Future[Option[JiraDetail]] =
    db.run( jiras.filter(_.id === id).result.headOption)

  def all: Future[List[JiraDetail]] =
    db.run(jiras.to[List].result)

  def create(depId:Long,jiraNo:String,jiraDesc:String,jiraStatus:String,fixVersion:String):Future[Long]={
    val dep = JiraDetail(0,depId, jiraNo,jiraDesc,jiraStatus,fixVersion)
    db.run(jiras returning jiras.map(_.id) += dep)
  }

  private[models] class JiraTable(tag: Tag) extends Table[JiraDetail](tag, "T_JIRA_DETAILS") {
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def depId=column[Long]("DEP_ID")
    def jiraNo = column[String]("JIRA_NO")
    def jiraDesc = column[String]("JIRA_DESC")
    def jiraStatus = column[String]("JIRA_STATUS")
    def fixVersion = column[String]("FIX_VERSION")


    def * = (id, depId,jiraNo, jiraDesc, jiraStatus,fixVersion) <>(JiraDetail.tupled, JiraDetail.unapply)

    def depFK = foreignKey("fk_dep_id", depId, depRepo.deployments)(_.id, onDelete = ForeignKeyAction.Cascade)

    def ? = (id.?,depId.?, jiraNo.?, jiraDesc.?,jiraStatus.?,fixVersion.?).
      shaped.<>({ r => import r._; _1.map(_ => JiraDetail.tupled((_1.get, _2.get, _3.get, _4.get, _5.get,_6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
  }

}
