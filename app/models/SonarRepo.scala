package models

import java.sql.Timestamp
import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.sql.SqlProfile.ColumnOption.SqlType

import scala.concurrent.Future

/**
  * Created by afssh on 14-08-2017.
  */
case class SonarDetail(id:Long,depId:Long,sonarId:String,blockers:Int,criticals:Int,majors:Int,time:String)
class SonarRepo @Inject()(val depRepo:DeployRepo)(val userRepo:UsersRepo)(val appRepo:AppsRepo)(protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._
  private[models] val sonars = TableQuery[SonarTable]

  def findSonarDetailsByDeplId(depId:Long):Future[List[SonarDetail]]={
    val action = sonars.filter(_.depId===depId).to[List]
    db.run(action.result)

  }


  def findById(id: Long): Future[Option[SonarDetail]] =
    db.run( sonars.filter(_.id === id).result.headOption)

  def all: Future[List[SonarDetail]] =
    db.run(sonars.to[List].result)

  def create(depId:Long,sonarId:String,blockers:Int,criticals:Int,majors:Int,time:String):Future[Long]={
    val dep = SonarDetail(0,depId, sonarId,blockers,criticals,majors,time)
    db.run(sonars returning sonars.map(_.id) += dep)
  }

  private[models] class SonarTable(tag: Tag) extends Table[SonarDetail](tag, "T_SONAR_DETAILS") {
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def depId=column[Long]("DEP_ID")
    def sonarId = column[String]("SONAR_ID")
    def blockers = column[Int]("BLOCKERS")
    def criticals = column[Int]("CRITICALS")
    def majors = column[Int]("MAJORS")
    def time=column[String]("SONAR_DATE")


    def * = (id, depId,sonarId, blockers, criticals,majors,time) <>(SonarDetail.tupled, SonarDetail.unapply)

    def depFK = foreignKey("fk_dep_id", depId, depRepo.deployments)(_.id, onDelete = ForeignKeyAction.Cascade)

    def ? = (id.?,depId.?, sonarId.?, blockers.?,criticals.?,majors.?,time.?).
      shaped.<>({ r => import r._; _1.map(_ => SonarDetail.tupled((_1.get, _2.get, _3.get, _4.get, _5.get,_6.get,_7.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
  }
}
