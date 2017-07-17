package controllers

import controllers.user.UserController
import play.api._
import play.api.mvc._

import services.user.UserServiceComponentImpl
import repositories.user.UserRepositoryComponentImpl

object Application extends UserController
                   with UserServiceComponentImpl
                   with UserRepositoryComponentImpl {

//  def index = Action {
//    Ok(views.html.index("Your new application is ready."))
//  }

}