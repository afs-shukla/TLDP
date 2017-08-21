package controllers

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
import org.joda.time.format.DateTimeFormat
import play.api.libs.json._
import play.api.libs.functional.syntax._
import com.typesafe.scalalogging.LazyLogging
import models._
import org.joda.time.DateTime
import play.api.libs.json.{JsResult, Format, JsValue, Json}
import play.api.mvc.{BaseController, ControllerComponents}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import play.api.libs.json.Json._
import play.api.libs.json._
/**
  * Created by afssh on 19-08-2017.
  */


case class DeployVo(id:Long,depNumber:String, userId:Long, appId: Long, depDate: String
                  , depEnvironment:String, depStatus:String, depRemarks:String,
                    jiraDetails:List[JiraDetail],
                    svnDetails:List[SvnDetail],
                    sonarDetails:List[SonarDetail])


class DeployController  @Inject()(implicit ec: ExecutionContext, depRepo: DeployRepo,svnRepo:SvnDepRepo,
                                  jiraRepo:JiraRepos,sonarRepo:SonarRepo,
                                  val controllerComponents: ControllerComponents) extends BaseController with LazyLogging {


  implicit object timestampFormat extends Format[Timestamp] {
    val format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS'Z'")
    def reads(json: JsValue) = {
      val str = json.as[String]
      JsSuccess(new Timestamp(format.parse(str).getTime))
    }
    def writes(ts: Timestamp) = JsString(format.format(ts))
  }

  implicit val targetJiraWrites = Json.writes[JiraDetail]
  implicit val targetJiraReads = Json.reads[JiraDetail]

  implicit val targetSvnWrites = Json.writes[SvnDetail]
  implicit val targetSvnReads = Json.reads[SvnDetail]

  implicit val targetSonarWrites = Json.writes[SonarDetail]
  implicit val targetSonarReads = Json.reads[SonarDetail]
  implicit val dployVoWrite: Writes[DeployVo] = (
    (JsPath \ "id").write[Long] and
      (JsPath \ "depNumber").write[String] and
      (JsPath \ "userId").write[Long] and
      (JsPath \ "appId").write[Long] and
      (JsPath \ "depDate").write[String] and
      (JsPath \ "depEnvironment").write[String] and
      (JsPath \ "depStatus").write[String] and
      (JsPath \ "depRemarks").write[String] and
      (JsPath \ "jiraDetails").write[List[JiraDetail]] and
      (JsPath \ "svnDetails").write[List[SvnDetail]] and
      (JsPath \ "sonarDetails").write[List[SonarDetail]]
    )(unlift(DeployVo.unapply))


  implicit val dployVoReads: Reads[DeployVo] = (
    (JsPath \ "id").read [Long] and
      (JsPath \ "depNumber").read[String] and
      (JsPath \ "userId").read[Long] and
      (JsPath \ "appId").read[Long] and
      (JsPath \ "depDate").read[String] and
      (JsPath \ "depEnvironment").read[String] and
      (JsPath \ "depStatus").read[String] and
      (JsPath \ "depRemarks").read[String] and
      (JsPath \ "jiraDetails").read[List[JiraDetail]] and
      (JsPath \ "svnDetails").read[List[SvnDetail]] and
      (JsPath \ "sonarDetails").read[List[SonarDetail]]
    )(DeployVo.apply _)



 /* implicit val targetDepVoWrites = Json.writes[DeployVo]
  implicit val targetDepVoReads = Json.reads[DeployVo]*/

  implicit val targetDepWrites = Json.writes[Deploy]
  implicit val targetDepReads = Json.reads[Deploy]




  def getAllDeployments = Action {

    val allDeployments = Await.result(depRepo.all, Duration.Inf)
    allDeployments match {
      case Nil => BadRequest(Json.prettyPrint(Json.obj(
        "status" -> "400",
        "message" -> s"Deployments List is empty")))
        Ok(Json.prettyPrint(Json.obj(
          "status" -> 200,
          "message" -> s"Deployments list size:${allDeployments.size}"))).as("json/application").as("text/plain")

      case x :: sx => {
        Ok(Json.prettyPrint(Json.obj(
          "status" -> 200,
          "appslist" -> allDeployments,
          "message" -> s"Deployments list size:${allDeployments.size}"))).as("json/application").as("text/plain")
      }
    }
  }



  def addDeployment= Action { implicit request =>
    val body = request.body
    val jsonBody:Option[JsValue] = body.asJson
    val formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")

    jsonBody.map {
      // println("Add app in controller:"+)
      json => {
        val dep = json.as[DeployVo]
        val id=Await.result(depRepo.create(dep.depNumber, dep.userId,dep.appId, new Timestamp(formatter.parseDateTime(dep.depDate).getMillis),
          dep.depEnvironment, dep.depStatus, dep.depRemarks), Duration.Inf)


        if (id > 0) {
          dep.jiraDetails foreach( jira=>Await.result(jiraRepo.create(id,jira.jiraNo,jira.jiraDesc,jira.jiraStatus,jira.fixVersion),Duration.Inf))


          dep.svnDetails foreach( svn=> Await.result(svnRepo.create(id,svn.fileNames,svn.version,svn.changedby,svn.time),Duration.Inf))

          dep.sonarDetails foreach( sonar=>Await.result(sonarRepo.create(id,sonar.sonarId,sonar.blockers,sonar.criticals,sonar.majors,sonar.time),Duration.Inf))

          Ok(Json.prettyPrint(Json.obj(
            "statusval id = Await.result(" -> 200,
            "message" -> s"Deployment:${dep.depNumber} Successfully Added"))).as("json/application").as("text/plain")
        }else{
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"Deployment:${dep.depNumber} is not added"))).as("json/application").as("text/plain")
        }


      }
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }

  }
  def updateDeployment= Action { implicit request =>
    val body = request.body
    val jsonBody:Option[JsValue] = body.asJson
    val formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")
    jsonBody.map {
       json => {
        val depVo=json.as[DeployVo]
         val dep=Deploy(depVo.id,depVo.depNumber, depVo.userId,depVo.appId,new Timestamp(formatter.parseDateTime(depVo.depDate).getMillis),
         depVo.depEnvironment, depVo.depStatus, depVo.depRemarks)
        val id = Await.result(depRepo.updateDeployment(dep),Duration.Inf)
        if(id>0) {
          logger.info("deployment is updated for id:"+id)
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"Deployment ${dep.depNumber} Successfully Updated"))).as("json/application").as("text/plain")
        }else {
          logger.error("deployment not found for id:"+id)
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"Deployment ${dep.depNumber} is Not Updated"))).as("json/application").as("text/plain")
        }
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }

  }



  def findDeploymentById(id:Long)=Action {
    val result = Await.result(depRepo.findById(id), Duration.Inf)
    result match {
      case Some(e) =>  {
        val jiraDetails=Await.result(jiraRepo.findJiraDetailsByDeplId(e.id),Duration.Inf)
        val svnDetails=Await.result(svnRepo.findSvnDetailsByDeplId(e.id),Duration.Inf)
        val sonarDetails=Await.result(sonarRepo.findSonarDetailsByDeplId(e.id),Duration.Inf)

        val deployResult=DeployVo(e.id,e.depNumber,e.userId,e.appId,e.depDate.toString,e.depEnvironment,e.depStatus,e.depRemarks
          ,jiraDetails,svnDetails ,sonarDetails)

        Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "appdetails" -> deployResult,
        "message" -> s"Deployment is found for id:${id}"))).as("json/application").as("text/plain")
      }

      case None =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "message" -> s"Deployment is not found for id :${id}"))).as("json/application").as("text/plain")
    }

  }

  private def checkForEmptyOrNull(field:String):Option[String]={
    if ( field !=null && (!field.isEmpty) )
      Some(field)
    else
      None
  }

  def searchDeploymentsByAppId(appId:Long)= Action {
    val result = Await.result(depRepo.findByAppId(appId), Duration.Inf)
    result match {
      case Nil => BadRequest(Json.prettyPrint(Json.obj(
        "status" -> "400",
        "message" -> s"Deployment List is empty"))).as("json/application").as("text/plain")
      case x :: sx => {
        Ok(Json.prettyPrint(Json.obj(
          "status" -> 200,
          "appslist" -> result,
          "message" -> s"Deployment list size:${result.size}"))).as("json/application").as("text/plain")
      }
    }
  }

  def searchDeploymentsByUserId(userId:Long)= Action {
    val result = Await.result(depRepo.findByUserId(userId), Duration.Inf)
    result match {
      case Nil => BadRequest(Json.prettyPrint(Json.obj(
        "status" -> "400",
        "message" -> s"Deployment List is empty"))).as("json/application").as("text/plain")
      case x :: sx => {
        Ok(Json.prettyPrint(Json.obj(
          "status" -> 200,
          "appslist" -> result,
          "message" -> s"Deployment list size:${result.size}"))).as("json/application").as("text/plain")
      }
    }
  }

  def searchDeploymentsByDate(date:Long)= Action {
    val result = Await.result(depRepo.findByDate(new Timestamp(new Date(date.longValue()).getTime())), Duration.Inf)
    result match {
      case Nil => BadRequest(Json.prettyPrint(Json.obj(
        "status" -> "400",
        "message" -> s"Deployment List is empty"))).as("json/application").as("text/plain")
      case x :: sx => {
        Ok(Json.prettyPrint(Json.obj(
          "status" -> 200,
          "appslist" -> result,
          "message" -> s"Deployment list size:${result.size}"))).as("json/application").as("text/plain")
      }
    }
  }

}
