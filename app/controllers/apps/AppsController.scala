package controllers.apps

import domain.apps.Apps
import play.mvc.Controller

/**
  * Created by afssh on 16-07-2017.
  */
class AppsController extends Controller{

  import play.api.libs.json._

  implicit val residentWrites = Json.writes[Apps]

  def findApp()

}
