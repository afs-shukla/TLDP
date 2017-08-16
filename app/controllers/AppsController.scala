package controllers

import java.lang.annotation.Target
import javax.inject.Inject

import models.{ AppsRepo ,TargetApps}
import play.api.libs.json.{JsPath, Writes}
import play.api.mvc._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * Created by afssh on 16-07-2017.
  */

//case class TargetApps(d:Long, appId:String, appName: String, appDesc:String, appType:String, appContainer:String, appDepType:String,
//                    appProjManager:String, appProcessName:String, appTeam:String, appRepository:String, appPlaybook:String)

//class AppsController @Inject()( cc: ControllerComponents) (implicit executionContext: ExecutionContext) extends AbstractController(cc) {
class AppsController @Inject()(implicit ec: ExecutionContext, appsReop: AppsRepo,
                              val controllerComponents: ControllerComponents) extends BaseController {


  def index = Action {
    Ok("Hello world")
  }
//  def getAllApps()= Action.async {
//   Ok("get all apps called")
//  }
/*
implicit val locationWrites: Writes[TargetApps] = (
  (JsPath \ "id").write[Long] and
    (JsPath \ "appId").write[String] and
    (JsPath \ "appName").write[String] and
    (JsPath \ "appDesc").write[String] and
    (JsPath \ "appType").write[String] and
    (JsPath \ "appContainer").write[String] and
    (JsPath \ "appDepType").write[String] and
    (JsPath \ "appProjManager").write[String] and
    (JsPath \ "appProcessName").write[String] and
    (JsPath \ "appTeam").write[String] and
    (JsPath \ "appRepository").write[String] and
    (JsPath \ "appPlaybook").write[String]
    )(unlift(TargetApps.unapply))
*/

  implicit val targetAppWrites = Json.writes[TargetApps]

  implicit val targetAppReads = Json.reads[TargetApps]






  def listRegisteredApps = Action {

    val allApps = Await.result(appsReop.all, Duration.Inf)
    allApps match {
      case Nil => BadRequest(Json.prettyPrint(Json.obj(
        "status" -> "400",
        "message" -> s"Application List is empty")))
        /*    Ok(Json.prettyPrint(Json.obj(
          "status" -> 200,
          "appslist" -> List(TargetApps( 1,"Action1","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123"),TargetApps(2, "Action1","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")),
          "message" -> s"Application list size:${allApps.size}"))).as("json/application").as("text/plain")
    }*/
        case x :: sx => {
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "appslist" -> List(TargetApps(1, "Action1", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123"), TargetApps(2, "Action1", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123")),
            "message" -> s"Application list size:${allApps.size}"))).as("json/application").as("text/plain")
        }
      }
    }



  def saveApp()={

  }
  def updateApp()={

  }

  def findById(id:String)=Action {
   // println("Find by id is called")
    Ok("Find by id called")
  }

  def search(id:String,name:String,desc:String)= Action {
    Ok("search apps called")
  }



}
