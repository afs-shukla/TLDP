package controllers

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.{LocalDateTime, LocalDate}
import javax.inject.Inject

import akka.util.ByteString
import com.typesafe.scalalogging.LazyLogging
import models._
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import play.api.http.HttpEntity


import play.api.libs.json.{Format, JsResult, Json, JsValue}
import play.api.libs.json.Json._

import play.api.mvc.{ResponseHeader, Result, BaseController, ControllerComponents}

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import scala.io.Source

/**
  * Created by afssh on 02-09-2017.
  */
case class ReleaseNotesVo(id:Long,depId:Long,relNoteType:String,relNoteName:String,relNoteData:String,relNoteDate:String)
class ReleaseNotesController @Inject()(implicit ec: ExecutionContext, relRepo:ReleaseNotesRepo, depRepo: DeployRepo,svnRepo:SvnDepRepo,
                                       jiraRepo:JiraRepos,sonarRepo:SonarRepo,
                                       val controllerComponents: ControllerComponents) extends BaseController with LazyLogging {

  val formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
  def timestampToDateTime(t: Timestamp): LocalDateTime =t.toLocalDateTime

  def dateTimeToTimestamp(dt: LocalDateTime): Timestamp =  Timestamp.valueOf(dt)

  implicit val timestampFormat = new Format[Timestamp] {

    def writes(t: Timestamp): JsValue = toJson(timestampToDateTime(t))

    def reads(json: JsValue): JsResult[Timestamp] = fromJson[LocalDateTime](json).map(dateTimeToTimestamp)

  }
  implicit val targetRelNoteWrites = Json.writes[ReleaseNotesVo]
  implicit val targetRelNoteReads = Json.reads[ReleaseNotesVo]




  def fetchAndFormatJiraList(depId:Long):ListBuffer[String] = {
    val jiraList = Await.result(jiraRepo.findJiraDetailsByDeplId(depId), Duration.Inf)
    val jiraListBuffer = ListBuffer[String]()

    jiraListBuffer +=(String.format("%-20s %-100s %-20s %-20s\r\n", "JIRA NO", "JIRA DESCRIPTION","JIRA STATUS","JIRA FIXVERSION"));
    //jiraListBuffer +=(String.format("%20s %100s %20s %20s\r\n", "Sonar ID", "Blockers","Criticals","Majors","Date"));
    jiraList foreach {
      jira =>{
        jiraListBuffer += String.format("%-20s %-100s %-20s %-20s\r\n", jira.jiraNo, jira.jiraDesc,jira.jiraStatus,jira.fixVersion);
      }
    }
    jiraListBuffer
  }

  def fetchAndFormatSvnList(depId:Long):ListBuffer[String] = {
    val svnList=Await.result(svnRepo.findSvnDetailsByDeplId(depId),Duration.Inf)
    val svnListBuffer=ListBuffer[String]()

    svnListBuffer +=(String.format("%-100s %-10s %-30s %-30s\r\n", "File Name", "Version","Changed By","Time"));
    svnList foreach {
      svn =>{
        svnListBuffer += String.format("%-100s %-10s %-30s %-30s\r\n", svn.fileNames, svn.version,svn.changedby,svn.time);
      }
    }
    svnListBuffer
  }

  def fetchAndFormatSonarList(depId:Long):ListBuffer[String] = {
    val sonarList=Await.result(sonarRepo.findSonarDetailsByDeplId(depId),Duration.Inf)
    val sonarListBuffer=ListBuffer[String]()

    sonarListBuffer +=(String.format("%-50s %-10s %-10s %-10s %-30s\r\n", "Sonar ID", "Blockers","Criticals","Majors","Date"));
    sonarList foreach {
      sonar =>{
        sonarListBuffer += String.format("%-50s %-10s %-10s %-10s %-30s\r\n", sonar.sonarId, sonar.blockers.toString,sonar.criticals.toString,sonar.majors.toString,sonar.time);
      }
    }

    sonarListBuffer
  }

  def createReleaseNoteHelper(depId:Long,data:String):String={
        val stringBuilder:StringBuilder=new StringBuilder
              stringBuilder.
              append("********************************************************************Release Notes****************************************************************************").append(System.getProperty("line.separator")).
              append("-------------------Manual Remarks ---------------------------------------------------------------------------------------------------------------------------").append(System.getProperty("line.separator")).
              append(data).append(System.getProperty("line.separator")).
              append("-------------------JIRA List---------------------------------------------------------------------------------------------------------------------------------").append(System.getProperty("line.separator")).
              append(formattedResult(fetchAndFormatJiraList(depId)).toString()).append(System.getProperty("line.separator")).
              append("-------------------SVN Details--------------------------------------------------------------------------------------------------------------------------------").append(System.getProperty("line.separator")).
              append(formattedResult(fetchAndFormatSvnList(depId)).toString()).append(System.getProperty("line.separator")).
              append("-------------------Sonar Static Code Analysis-----------------------------------------------------------------------------------------------------------------").append(System.getProperty("line.separator")).
              append(formattedResult(fetchAndFormatSonarList(depId)).toString()).
              append("--------------------------------------------------------------------------------------------------------------------------------------------------------------").append(System.getProperty("line.separator"))
              .toString()
  }
 def formattedResult(listBuffer:ListBuffer[String]):StringBuilder={
   val stringBuilder:StringBuilder=new StringBuilder
   listBuffer foreach(row => stringBuilder.append(row))
   stringBuilder
 }
 /* def index = Action {

    val file = new java.io.File("/tmp/fileToServe.pdf")
    val path: java.nio.file.Path = file.toPath
    val source: Source[ByteString, _] = FileIO.fromPath(path)

    Result(
      header = ResponseHeader(200, Map.empty),
      body = HttpEntity.Streamed(source, Some(file.length), Some("application/pdf"))
    )
  }*/

 def getReleaseNote(depId:Long)=Action {
    val result = Await.result(relRepo.findByDepId(depId), Duration.Inf)

    result match {
      case Some(e) => {
        val source: Source = Source.fromString(e)
        Result(
          header = ResponseHeader(200, Map.empty),
          body = HttpEntity.Strict(ByteString(e),Some("application/pdf"))//HttpEntity.Streamed(source, Some(file.length), Some("application/pdf"))
        )
      }
      case None =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "message" -> s"Release not not found"))).as("json/application").as("text/plain")
        }

  }

  def createReleaseNote= Action { implicit request =>
    val body = request.body
    val jsonBody:Option[JsValue] = body.asJson
    //case class ReleaseNotes(id:Long,depId:Long,relNoteType:String,relNoteName:String,relNoteData:String,relNoteDate:Timestamp)
    jsonBody.map {
      // println("Add app in controller:"+)
      json => {
        val ta=json.as[ReleaseNotesVo]
        val notes=createReleaseNoteHelper(ta.depId,ta.relNoteData)
        //println("========================================================================= Start")
        println(notes)
        //println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ END")
        val id = Await.result(relRepo.create(ta.depId,ta.relNoteType,ta.relNoteName,notes,new Timestamp(formatter.parse(ta.relNoteDate).getTime)),Duration.Inf)
        logger.info("Issueee !!!!!!!!!!!!!!!!!!!!!!!!!"+id)
        if(id>0) {
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"Release Note for ${ta.relNoteName} Successfully created"))).as("json/application").as("text/plain")
        }else{
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"Release Note for ${ta.relNoteName} is Not created"))).as("json/application").as("text/plain")
        }
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }

  }

}
