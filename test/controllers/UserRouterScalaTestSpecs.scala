package controllers

import models.{UsersRepo, AppsRepo}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.{FakeRequest, FakeHeaders}
import play.api.test.Helpers._
import testhelpers.Injector
case class UsersDetail(id:Long, userId:String, userFirstName: String, userLastName:String
                       , userManager:String, userProcessName:String, userTeam:String, userPhone:String, userEmail:String)

/**
  * Created by afssh on 25-08-2017.
  */
class UserRouterScalaTestSpecs  extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach  {
  val appRepo = Injector.inject[UsersRepo]


  "Controller tests- AppsController" should {

    "adding user" in {
      val userdetail: String =
        s"""{"id":0, "userId":"User1002" , "userFirstName":"userFirstName Testing","userLastName":"userLastName to connect",
                "userManager":"userManager","userProcessName":"userProcessName 5","userTeam":"userTeam",
                "userPhone":"345345345345","userEmail":"afs.sjikla@gmail.com"}"""

      val fakeheaders = FakeHeaders(Seq("Content-type" -> "application/json"))
      val fakeRequest = FakeRequest(method = "POST", uri = "/addUser", headers = fakeheaders, userdetail)
      val Some(result) = route[String](app, fakeRequest)
      println("Result Add User:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      contentAsString(result) must include("Successfully Added")
    }

  }
}
