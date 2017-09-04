package models

import java.sql.{Clob, Timestamp}
import java.time.LocalDateTime
import javax.inject.Inject

import com.typesafe.scalalogging.LazyLogging
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

/**
  * Created by afssh on 02-09-2017.
  */

case class ReleaseNotes(id:Long,depId:Long,relNoteType:String,relNoteName:String,relNoteData:String,relNoteDate:Timestamp)
class ReleaseNotesRepo  @Inject() (protected val dbConfigProvider: DatabaseConfigProvider) extends LazyLogging{

  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val db = dbConfig.db

  import dbConfig.profile.api._

  val releaseNotes = TableQuery[ReleaseNoteTable]


  private def _findByDepIdAndRelType(depId:Long,relNoteType:String):DBIO[Option[ReleaseNotes]]={
    releaseNotes.filter(x=>x.depId===depId && x.relNoteType===relNoteType).result.headOption
  }

  private def _findById(id: Long): DBIO[Option[ReleaseNotes]] =
    releaseNotes.filter(_.id === id).result.headOption

/*
  private def _findByDepId(depId: Long): Query[ReleaseNoteTable, ReleaseNotes, List] =
    releaseNotes.filter(_.depId === depId).to[List]
*/

  private def _findByDepId(depId: Long): DBIO[Option[String]]=
    releaseNotes.filter(_.depId === depId).map(_.relNoteData).result.headOption


  def findByDepIdAndRelType(depId:Long,relNoteType:String):Future[Option[ReleaseNotes]]=
    db.run(_findByDepIdAndRelType(depId,relNoteType))

  def findById(id: Long): Future[Option[ReleaseNotes]] =
    db.run(_findById(id))

  def findByDepId(depId: Long): Future[Option[String]] =
    db.run(_findByDepId(depId))

  def all: Future[List[ReleaseNotes]] =
    db.run(releaseNotes.to[List].result)
  def create(depId: Long,relNoteType:String,relNoteName:String,relNoteData:String,relNoteDate:Timestamp): Future[Long] = {
    val project = ReleaseNotes(0, depId,relNoteType,relNoteName,relNoteData,relNoteDate)
    //logger.info("Adding application :"+project)
    db.run(releaseNotes returning releaseNotes.map(_.id) += project)
  }

  def delete(id: Long): Future[Int] = {
    val action = releaseNotes.filter(_.id === id).delete

    db.run(action.transactionally)
  }

  class ReleaseNoteTable(tag: Tag) extends Table[ReleaseNotes](tag, "T_RELEASE_NOTES") {
    def id=column[Long]("ID",O.AutoInc,O.PrimaryKey)
    def depId = column[Long]("DEP_ID")
    def relNoteType = column[String]("REL_NOTE_TYPE")
    def relNoteName=column[String]("RELEASE_NOTE_NAME")
    def relNoteData=column[String]("RELEASE_NOTE_DATA", O.SqlType("CLOB"))
    def relNoteDate=column[Timestamp]("RELNOTE_DATE")



    def * = (id, depId,relNoteType,relNoteName,relNoteData,relNoteDate) <>(ReleaseNotes.tupled, ReleaseNotes.unapply)

    def ? = (id.?, depId.?,relNoteType.?,relNoteName.?,relNoteData.?,relNoteDate.?).
      shaped.<>({ r => import r._; _1.map(_ => ReleaseNotes.tupled((_1.get, _2.get,_3.get,_4.get,_5.get,_6.get))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

  }
}
