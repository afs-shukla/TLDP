package controllers

import models.AppsRepo
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.{FakeHeaders, FakeRequest}
import play.api.test.Helpers._
import testhelpers.Injector

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by afssh on 15-08-2017.
  */
  class AppsRouterScalaTestSpecs  extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach  {
    val appRepo = Injector.inject[AppsRepo]


    "Controller tests- AppsController" should {

     "adds app" in {
       val appdetail: String =
         """{"id":0, "appId":"App001" , "appName":"Adapter Testing","appDesc":"Used to connect",
                "appType":"Java Web App","appContainer":"Tomcat 5","appDepType":"Ansible",
                "appProjManager":"Satish Shukla","appProcessName":"GMOT","appTeam":"Magnus",
                "appRepository":"http://localhost:9090/nexus/gmot/","appPlaybook":"App001Playbook"
            }"""
       val fakeheaders = FakeHeaders(Seq("Content-type" -> "application/json"))
       val fakeRequest = FakeRequest(method = "POST", uri = "/addApp", headers = fakeheaders, appdetail)
       val Some(result) = route[String](app, fakeRequest)
       println("Result Add App:" + result)
       println(contentAsJson(result))
       status(result) mustEqual OK
       contentAsString(result) must include("Successfully Added")
     }

     "search applications" in {
       val Some(result) = route(app, FakeRequest(GET, s"/searchApps/${}/${"Adapter"}/${}"))
       println("Result:" + result)
       status(result) mustEqual OK
       println(contentAsJson(result))
       contentType(result) mustEqual Some("text/plain")
       //charset(result) mustEqual Some("utf-8")
       //contentAsString(result) must include("ansible")
     }

     "update target" in {
       val appdetail: String =
         """{"id":1, "appId":"App999" , "appName":"Adapter Testing","appDesc":"Used to connect",
                "appType":"Java Web App","appContainer":"Tomcat 5","appDepType":"Ansible",
                "appProjManager":"Satish Shukla","appProcessName":"GMOT","appTeam":"Magnus",
                "appRepository":"http://localhost:9090/nexus/gmot/","appPlaybook":"App001Playbook"
            }"""
       val fakeheaders = FakeHeaders(Seq("Content-type" -> "application/json"))
       val fakeRequest = FakeRequest(method = "POST", uri = "/updateApp", headers = fakeheaders, appdetail)
       val Some(result) = route[String](app, fakeRequest)
       println("Result Update App:" + result)
       println(contentAsJson(result))
       status(result) mustEqual OK
       contentAsString(result) must include("Successfully Updated")
     }

     "Get all apps returns all apps" in {
       val Some(result) = route(app, FakeRequest(GET, "/getAllApps"))
       println("Result:" + result)
       status(result) mustEqual OK
       println(contentAsJson(result))
       contentType(result) mustEqual Some("text/plain")
       //charset(result) mustEqual Some("utf-8")
       //contentAsString(result) must include("ansible")
     }


     "find by id application" in {
       val Some(result) = route(app, FakeRequest(GET, s"/findById/${1}"))
       println("Result:" + result)
       println(contentAsJson(result))
       status(result) mustEqual OK
       println(contentAsJson(result))
       contentType(result) mustEqual Some("text/plain")
       //charset(result) mustEqual Some("utf-8")
       contentAsString(result) must include("Application is found")
     }

     "remove application" in {
       val Some(result) = route(app, FakeRequest(GET, s"/removeApp/${1}"))
       println("Result:" + result)
       println(contentAsJson(result))
       status(result) mustEqual OK
       println(contentAsJson(result))
       contentType(result) mustEqual Some("text/plain")
       //charset(result) mustEqual Some("utf-8")
       contentAsString(result) must include("Successfully Removed")
     }

     "Post removeal apps returns all apps" in {
       val Some(result) = route(app, FakeRequest(GET, "/getAllApps"))
       println("Result:" + result)
       status(result) mustEqual OK
       println(contentAsJson(result))
       contentType(result) mustEqual Some("text/plain")
       //charset(result) mustEqual Some("utf-8")
       //contentAsString(result) must include("ansible")
     }


   }
}
