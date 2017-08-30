package controllers

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date

import models.{UsersRepo, DeployRepo, AppsRepo}
import org.joda.time.DateTime
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.{FakeRequest, FakeHeaders}
import play.api.test.Helpers._
import testhelpers.Injector

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by afssh on 19-08-2017.
  */



class DeploymentRouterScalaTestSpecs extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach  {
  val depRepo = Injector.inject[DeployRepo]
  val appRepo=Injector.inject[AppsRepo]
  val userRepo=Injector.inject[UsersRepo]

/*  case class Deploy(id:Long,depNumber:String, userId:Long, appId: Long, depDate: Timestamp
                    , depEnvironment:String, depStatus:String, depRemarks:String)*/
 "Controller tests- DeploymentController" should{
   "adds deps" in {
     val taimestamp = new Date().getTime
     /** * Application is created */
     val action2 = appRepo.create("A002", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123")
     val app2 = Await.result(action2, Duration.Inf)
     /** ** User is created *****/
     val user1 = userRepo.create("S0001", "Satish", "Shukla", "Apptype", "tomcat", "ansible", "32454353453", "afs.shukla@gmail.com")
     val ur1 = Await.result(user1, Duration.Inf)

     val depDetail: String =
       s"""{"id":0, "depNumber":"Dep001" , "userId":1,"appId":1,
                "depDate":"20/08/2017 09:10:00","depEnvironment":"UAT","depStatus":"Successful",
                "depRemarks":"Test deployment","fixVersion":"fixVersion",
                "jiraDetails":[{"id":0,"depId":0, "jiraNo":"JIRA-0320","jiraDesc": "Creating New accunt for clinet","jiraStatus": "Completed","fixVersion":"10.44.22"},
     {"id":0,"depId":0, "jiraNo":"JIRA-0321","jiraDesc": "Creating New Service for clinet","jiraStatus": "Completed","fixVersion":"10.44.22"}],
       "svnDetails":[{"id":0,"depId":0,"fileNames":"applciation.conf","version":"3000","changedby":"Stish","time":"10-05-2017" },
       {"id":0,"depId":0,"fileNames":"build.gradle","version":"3000","changedby":"Stish","time":"10-05-2017" }],
       "sonarDetails":[{"id":0,"depId":0,"sonarId":"Sonar0011","blockers":1,"criticals":3,"majors":10,"time":"10-03-2017"}]}"""


     /*"jiraDetails":[{"id":0,"depId":0, "jiraNo":"JIRA-0320","jiraDesc": "Creating New accunt for clinet","jiraStatus": "Completed","fixVersion":"10.44.22"},
     {"id":0,"depId":0, "jiraNo":"JIRA-0321","jiraDesc": "Creating New Service for clinet","jiraStatus": "Completed","fixVersion":"10.44.22"}],
       "svnDetails":[{"id":0,"depId":0,"fileNames":"applciation.conf","version":"3000","changedby":"Stish","time":"10-05-2017" },
       {"id":0,"depId":0,"fileNames":"build.gradle","version":"3000","changedby":"Stish","time":"10-05-2017" }],
       "sonarDetails":[{"id":0,"depId":0,"sonarId":"Sonar0011","blockers":1,"criticals":3,"majors":10,"time":"10-03-2017"}]}"""*/

     val fakeheaders = FakeHeaders(Seq("Content-type" -> "application/json"))
     val fakeRequest = FakeRequest(method = "POST", uri = "/addDeployment", headers = fakeheaders, depDetail)
     val Some(result) = route[String](app, fakeRequest)
     println("Result Deploymets:" + result)
     println(contentAsJson(result))
     status(result) mustEqual OK
     contentAsString(result) must include("Successfully Added")
   }
 }

  "update deploymets tests" should {

   /* "update deployment" in {

      /** * Application is created */
      val action2 = appRepo.create("A002", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123")
      val app2 = Await.result(action2, Duration.Inf)
      /** ** User is created *****/
      val user1 = userRepo.create("S0001", "Satish", "Shukla", "Apptype", "tomcat", "ansible", "32454353453", "afs.shukla@gmail.com")
      val ur1 = Await.result(user1, Duration.Inf)

      val deploy1=Await.result(depRepo.create("DEP001",ur1,app2,new Timestamp(new Date().getTime),"SIT","SUCCESS","Dummy Deployment","fixVersion"),Duration.Inf)


      println("find by id "+Await.result(depRepo.findById(deploy1),Duration.Inf))
      val taimestamp = new Date().getTime
      val depDetail: String =
        s"""{"id":${deploy1}, "depNumber":"Dep002" , "userId":1,"appId":1,
                "depDate":"20/08/2017 09:10:00","depEnvironment":"UAT","depStatus":"Successful",
                "depRemarks":"Test deployment","fixVersion":"fixVersion","jiraDetails":[],"svnDetails":[],"sonarDetails":[]}"""

      val fakeheaders = FakeHeaders(Seq("Content-type" -> "application/json"))
      val fakeRequest = FakeRequest(method = "POST", uri = "/updateDeployment", headers = fakeheaders, depDetail)
      val Some(result) = route[String](app, fakeRequest)
      println("Result Deploymets:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      contentAsString(result) must include("Successfully Updated")
    }
    "Get all Deployments " in {
      val Some(result) = route(app, FakeRequest(GET, "/getAllDeployments"))
      println("Result:" + result)
      status(result) mustEqual OK
      println(contentAsJson(result))
      contentType(result) mustEqual Some("text/plain")
      //charset(result) mustEqual Some("utf-8")
      //contentAsString(result) must include("ansible")
    }
   "find by id deployments" in {
      val Some(result) = route(app, FakeRequest(GET, s"/findDeploymentById/${1}"))
      println("Result:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      println(contentAsJson(result))
      contentType(result) mustEqual Some("text/plain")
      //charset(result) mustEqual Some("utf-8")
      contentAsString(result) must include("UAT")
    }
    "find by AppId deployments" in {
      val Some(result) = route(app, FakeRequest(GET, s"/searchDeploymentsByAppId/${1}"))
      println("Result:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      println(contentAsJson(result))
      contentType(result) mustEqual Some("text/plain")
      //charset(result) mustEqual Some("utf-8")
      contentAsString(result) must include("UAT")
    }
    "find by UserId deployments" in {
      val Some(result) = route(app, FakeRequest(GET, s"/searchDeploymentsByUserId/${1}"))
      println("Result:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      println(contentAsJson(result))
      contentType(result) mustEqual Some("text/plain")
      //charset(result) mustEqual Some("utf-8")
      contentAsString(result) must include("UAT")
    }*/
   /* "find by Date deployments" in {
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val date = sdf.parse(sdf.format(new Date()))
      val taimestamp=date.getTime

      println("Convert To date:"+new Date(taimestamp))
      val Some(result) = route(app, FakeRequest(GET, s"/searchDeploymentsByDate/${taimestamp}"))
      println("Result:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      println(contentAsJson(result))
      contentType(result) mustEqual Some("text/plain")
      //charset(result) mustEqual Some("utf-8")
      contentAsString(result) must include("UAT")
    }*/

   /* "Search deployments" in {
      val sdf = new SimpleDateFormat("yyyy-MM-dd")
      val date = sdf.parse(sdf.format(new Date()))
      val taimestamp=date.getTime

      println("Convert To date:"+new Date(taimestamp))
      val Some(result) = route(app, FakeRequest(GET, s"/searchDeployments/${"Dep"}/${1}/${1}/${}/${}/${}"))
      println("Result:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      println(contentAsJson(result))
      contentType(result) mustEqual Some("text/plain")
      //charset(result) mustEqual Some("utf-8")
      contentAsString(result) must include("UAT")
    }*/
  }
}