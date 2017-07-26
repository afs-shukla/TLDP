package controllers.apps

import javax.inject.Inject


import play.api.mvc._
import play.mvc.Controller

import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


/**
  * Created by afssh on 16-07-2017.
  */


  class AppsController @Inject()(      controllerComponents: ControllerComponents) (implicit executionContext: ExecutionContext) extends AbstractController(controllerComponents)
  {


    /* import play.api.libs.json._
     val db = Database.forConfig("slick.dbs.default")
     implicit val residentWrites = Json.writes[Apps]*/
    def app="satish"


}
