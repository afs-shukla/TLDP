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

  "Testing Apps Routers " should {

    val action1 = appRepo.create( "Action1","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
    val result1 = Await.result(action1, Duration.Inf)
    println("Result1"+result1)
    val action2 = appRepo.create( "Action2","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
    val result2 = Await.result(action2, Duration.Inf)
    println("Result2"+result2)


    "returns all apps" in  {
      val Some(result) = route(app, FakeRequest(GET, "/getAllApps"))
      println("Result:"+result)
      status(result) mustEqual OK
      println(contentAsJson(result))
      contentType(result) mustEqual Some("text/plain")
      //charset(result) mustEqual Some("utf-8")
      contentAsString(result) must include("ansible")
    }

    "adds app" in {
      val appdetail:String= """{ "appId":"App001" , "appName":"Adapter Testing","appDesc":"Used to connect",
                "appType":"Java Web App","appContainer":"Tomcat 5","appDepType":"Ansible",
                "appProjManager":"Satish Shukla","appProcessName":"GMOT","appTeam":"Magnus",
                "appRepository":"http://localhost:9090/nexus/gmot/","appPlaybook":"App001Playbook"
            }"""


     val  fakeheaders = FakeHeaders( Seq("Content-type"->"application/json"))


     val fakeRequest=FakeRequest(method="POST",uri="/addApp", headers = fakeheaders,appdetail)

      val Some(result) = route[String](app, fakeRequest)
      println("Result Add App:"+result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      contentAsString(result) must include("Successfully saved")
    }
  }
}
