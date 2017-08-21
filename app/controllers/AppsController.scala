package controllers

import java.lang.annotation.Target
import javax.inject.Inject

import com.typesafe.scalalogging.LazyLogging
import models.{TargetAppsVo, AppsRepo, TargetApps}
import play.api.libs.json.{JsPath, Writes}


import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}
import play.api._
import play.api.mvc._
import play.api.libs.json._


/**
  * Created by afssh on 16-07-2017.
  */


class AppsController @Inject()(implicit ec: ExecutionContext, appsReop: AppsRepo,
                              val controllerComponents: ControllerComponents) extends BaseController with LazyLogging {

  implicit val targetAppWrites = Json.writes[TargetApps]
  implicit val targetAppReads = Json.reads[TargetApps]

  def listRegisteredApps = Action {

    val allApps = Await.result(appsReop.all, Duration.Inf)
    allApps match {
      case Nil => BadRequest(Json.prettyPrint(Json.obj(
        "status" -> "400",
        "message" -> s"Application List is empty")))
            Ok(Json.prettyPrint(Json.obj(
          "status" -> 200,
          "message" -> s"Application list size:${allApps.size}"))).as("json/application").as("text/plain")

        case x :: sx => {
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "appslist" -> allApps,
            "message" -> s"Application list size:${allApps.size}"))).as("json/application").as("text/plain")
        }
      }
    }



 def addApp= Action { implicit request =>
    val body = request.body
    val jsonBody:Option[JsValue] = body.asJson

    jsonBody.map {
         // println("Add app in controller:"+)
      json => {
        val ta=json.as[TargetApps]
        val id = Await.result(appsReop.create(ta.appId,ta.appName,ta.appDesc,ta.appType,ta.appContainer,ta.appDepType,ta.appProjManager,
          ta.appProcessName,ta.appTeam,ta.appRepository,ta.appPlaybook),Duration.Inf)
       if(id>0) {
         Ok(Json.prettyPrint(Json.obj(
           "status" -> 200,
           "message" -> s"${ta.appName} Successfully Added"))).as("json/application").as("text/plain")
       }else{
         Ok(Json.prettyPrint(Json.obj(
           "status" -> 200,
           "message" -> s"${ta.appName} is Not Added"))).as("json/application").as("text/plain")
       }
       }
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }

   }
  def updateApp= Action { implicit request =>
    val body = request.body
    val jsonBody:Option[JsValue] = body.asJson

    jsonBody.map {
      // println("Add app in controller:"+)
      json => {
        val ta=json.as[TargetApps]
        val id = Await.result(appsReop.updateApp(ta),Duration.Inf)
        if(id>0) {

          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"${ta.appName} Successfully Updated"))).as("json/application").as("text/plain")
        }else{
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"${ta.appName} is Not Updated"))).as("json/application").as("text/plain")
        }
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }

  }



  def findById(id:Long)=Action {
    val result = Await.result(appsReop.findById(id), Duration.Inf)
    result match {
      case Some(e) =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "appdetails" -> e,
        "message" -> s"Application is found for id:${id}"))).as("json/application").as("text/plain")

      case None =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "message" -> s"Application is not found for id :${id}"))).as("json/application").as("text/plain")
    }

  }

  def removeApp(id:Long)= Action {
    val result = Await.result(appsReop.delete(id), Duration.Inf)
    result match {
      case 1 =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "message" -> s"Successfully Removed"))).as("json/application").as("text/plain")

      case _ =>  Ok(Json.prettyPrint(Json.obj(
        "status" -> 200,
        "message" -> s"Application is not removed for id :${id}"))).as("json/application").as("text/plain")
    }

  }

  private def checkForEmptyOrNull(field:String):Option[String]={
    if ( field !=null && (!field.isEmpty) )
      Some(field)
    else
      None
  }


  def searchApps(appId:String,name:String,desc:String)= Action {
    val result = Await.result(appsReop.appSearch(checkForEmptyOrNull(appId),checkForEmptyOrNull(name),checkForEmptyOrNull(desc)), Duration.Inf)
    result match {
      case Nil => BadRequest(Json.prettyPrint(Json.obj(
        "status" -> "400",
        "message" -> s"Application List is empty"))).as("json/application").as("text/plain")
     case x :: sx => {
        Ok(Json.prettyPrint(Json.obj(
          "status" -> 200,
          "appslist" -> result,
          "message" -> s"Application list size:${result.size}"))).as("json/application").as("text/plain")
      }
    }
  }



}
