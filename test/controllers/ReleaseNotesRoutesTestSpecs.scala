package controllers

import java.text.SimpleDateFormat
import java.util.Date

import models.{UsersRepo, DeployRepo, ReleaseNotesRepo, AppsRepo}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.{FakeRequest, FakeHeaders}
import play.api.test.Helpers._
import testhelpers.Injector

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by afssh on 02-09-2017.
  */
class ReleaseNotesRoutesTestSpecs  extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach  {
  val relRepo = Injector.inject[ReleaseNotesRepo]
  val depRepo = Injector.inject[DeployRepo]
  val appRepo=Injector.inject[AppsRepo]
  val userRepo=Injector.inject[UsersRepo]
  val formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
  "Release Notes " should {

    "be added" in {
      val taimestamp = new Date().getTime
      /** * Application is created */
      val action2 = appRepo.create("A002", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123")
      val app2 = Await.result(action2, Duration.Inf)
      /** ** User is created *****/
      val user1 = userRepo.create("S0001", "Satish", "Shukla", "Apptype", "tomcat", "ansible", "32454353453", "afs.shukla@gmail.com")
      val ur1 = Await.result(user1, Duration.Inf)
      val date=formatted.format(new Date)
      val depDetail: String =
        s"""{"id":0, "depNumber":"Dep001" , "userId":1,"appId":1,
                "depDate":"${date}","depEnvironment":"UAT","depStatus":"Successful",
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
      println("Result:" + result)


       val relNote=s"""{"id":0,"depId":1,"relNoteType":"UAT Release","relNoteName":"Release Note for UAT","relNoteData":"Dummy DATA","relNoteDate":${date}"""

      val fakeRequest1 = FakeRequest(method = "POST", uri = "/createReleaseNote", headers = fakeheaders, relNote)
      val Some(result1) = route[String](app, fakeRequest1)
      println("Result1:" + result1)
      //status(result1) mustEqual OK
      println(contentAsJson(result))
      contentType(result) mustEqual Some("text/plain")







    }



  }

}
