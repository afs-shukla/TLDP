package controllers

import javax.inject.Inject

import play.api.mvc._

import scala.concurrent.ExecutionContext


/**
  * Created by afssh on 16-07-2017.
  */


class AppsController @Inject()( cc: ControllerComponents) (implicit executionContext: ExecutionContext) extends AbstractController(cc) {


//  def getAppbyId(id:String)= Action {
//implicit  request => Ok()
//  }
  def index = Action {
    Ok("Hello world")
  }
  def getAllApps()= Action {
   Ok("get all apps called")
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
