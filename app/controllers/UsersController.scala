package controllers

import javax.inject.Inject

import com.typesafe.scalalogging.LazyLogging
import models.{UsersDetail, UsersRepo, TargetApps, AppsRepo}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.{BaseController, ControllerComponents}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext}


/**
  * Created by afssh on 25-08-2017.
  */
class UsersController @Inject()(implicit ec: ExecutionContext, usersRepo: UsersRepo,
                               val controllerComponents: ControllerComponents) extends BaseController with LazyLogging {

  implicit val userWrites = Json.writes[UsersDetail]
  implicit val userReads = Json.reads[UsersDetail]


  def addUser= Action { implicit request =>
    val body = request.body
    val jsonBody:Option[JsValue] = body.asJson


    jsonBody.map {
      // println("Add app in controller:"+)
      json => {
        val ta=json.as[UsersDetail]
        val id = Await.result(usersRepo.create(ta.userId,ta.userFirstName,ta.userLastName,ta.userManager,ta.userProcessName,ta.userTeam,ta.userPhone,
          ta.userEmail),Duration.Inf)
        if(id>0) {
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"${ta.userFirstName} Successfully Added"))).as("json/application").as("text/plain")
        }else{
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"${ta.userFirstName} is Not Added"))).as("json/application").as("text/plain")
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
        val ta=json.as[UsersDetail]
        val id = Await.result(usersRepo.updateUser(ta),Duration.Inf)
        if(id>0) {

          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"${ta.userFirstName} Successfully Updated"))).as("json/application").as("text/plain")
        }else{
          Ok(Json.prettyPrint(Json.obj(
            "status" -> 200,
            "message" -> s"${ta.userFirstName} is Not Updated"))).as("json/application").as("text/plain")
        }
      }
    }.getOrElse {
      BadRequest("Expecting application/json request body")
    }

  }

}
