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


  class AppsController @Inject()( cc: ControllerComponents) (implicit executionContext: ExecutionContext) extends AbstractController(cc){


//  def getAppbyId(id:String)= Action {
//implicit  request => Ok()
//  }
  def index = Action {
    Ok("Hello world")
  }
  def getAllApps()={

  }
  def saveApp()={

  }
  def updateApp()={

  }

  def search(id:String,name:String,desc:String)={

  }



}
