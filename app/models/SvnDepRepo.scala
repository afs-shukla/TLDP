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


case class SvnDetail(id:Long, depId:Long, fileNames: String, version:String, changedby:String,time:String)

class SvnDepRepo  @Inject()(val depRepo:DeployRepo) (protected val dbConfigProvider: DatabaseConfigProvider) {

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._

  val SvnDetails = TableQuery[SvnTable]

  def findSvnDetailsByDeplId(depId:Long):Future[List[SvnDetail]]={
    val action = SvnDetails.filter(_.depId===depId).to[List]
    db.run(action.result)

  }

  def findById(id: Long): Future[Option[SvnDetail]] =
    db.run( SvnDetails.filter(_.id === id).result.headOption)

  def all: Future[List[SvnDetail]] =
    db.run(SvnDetails.to[List].result)

  def create(depId:Long, fileNames: String, version:String, changedby:String,time:String):Future[Long]={
    val dep = SvnDetail(0,depId, fileNames,version,changedby,time)
    db.run(SvnDetails returning SvnDetails.map(_.id) += dep)
  }
  private[models] class SvnTable(tag: Tag) extends Table[SvnDetail](tag, "T_SVN_DETAIL") {
    def id = column[Long]("ID", O.AutoInc, O.PrimaryKey)
    def depId=column[Long]("DEP_ID")
    def fileNames = column[String]("FILE_NAMES")
    def version = column[String]("VERSION")
    def changedby = column[String]("CHANGED_BY")
    def time = column[String]("CHANGE_DATE")


    def * = (id, depId,fileNames, version, changedby,time) <>(SvnDetail.tupled, SvnDetail.unapply)

    def defFK = foreignKey("fk_dep_id", depId, depRepo.deployments)(_.id, onDelete = ForeignKeyAction.Cascade)

    def ? = (id.?,depId.?, fileNames.?, version.?, changedby.?,time.?).
      shaped.<>({ r => import r._; _1.map(_ => SvnDetail.tupled((_1.get, _2.get, _3.get, _4.get, _5.get,_6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))
  }

}
