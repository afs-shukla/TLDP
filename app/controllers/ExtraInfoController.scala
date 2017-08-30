package controllers

import javax.inject.Inject

import com.typesafe.scalalogging.LazyLogging
import models._
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

/**
  * Created by afssh on 26-08-2017.
  */
class ExtraInfoController @Inject()(implicit ec: ExecutionContext, svnDepRepo: SvnDepRepo,sonarRepo:SonarRepo,jiraRepo:JiraRepos,
                                    val controllerComponents: ControllerComponents) extends BaseController with LazyLogging {

  implicit val jiraWrites = Json.writes[JiraDetail]
  implicit val jiraReads = Json.reads[JiraDetail]

  implicit val svnWrites = Json.writes[SvnDetail]
  implicit val svnReads = Json.reads[SvnDetail]

  implicit val sonarWrites = Json.writes[SonarDetail]
  implicit val sonarReads = Json.reads[SonarDetail]


  def findJiraDetailsByDepId(depId:Long)=Action {
    val result = Await.result(jiraRepo.findJiraDetailsByDeplId(depId), Duration.Inf)
    result match {
      case s::xs =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "jiradetails" -> result,
        "message" -> s"JIRA Details  found for id:${depId}"))).as("json/application").as("text/plain")

      case Nil =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "message" -> s"JIRA Details  not found for id :${depId}"))).as("json/application").as("text/plain")
    }

  }

  def findSvnDetailsByDepId(depId:Long)=Action {
    val result = Await.result(svnDepRepo.findSvnDetailsByDeplId(depId), Duration.Inf)
    result match {
      case s::sx =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "svndetails" -> result,
        "message" -> s"SVN Details  found for id:${depId}"))).as("json/application").as("text/plain")

      case Nil =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "message" -> s"SVN Details  not found for id :${depId}"))).as("json/application").as("text/plain")
    }

  }

  def findSonarDetailsByDepId(depId:Long)=Action {
    val result = Await.result(sonarRepo.findSonarDetailsByDeplId(depId), Duration.Inf)
    result match {
      case s::xs =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "sonardetails" -> result,
        "message" -> s"Sonar Details  found for id:${depId}"))).as("json/application").as("text/plain")

      case Nil =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "message" -> s"Sonar Details  not found for id :${depId}"))).as("json/application").as("text/plain")
    }

  }

}
