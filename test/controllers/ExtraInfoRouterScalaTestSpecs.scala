package controllers

import models.{JiraRepos, SvnDepRepo, SonarRepo, UsersRepo}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.test.{FakeRequest, FakeHeaders}
import play.api.test.Helpers._
import testhelpers.Injector

/**
  * Created by afssh on 26-08-2017.
  */
class ExtraInfoRouterScalaTestSpecs extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach  {
  val sonarRepo = Injector.inject[SonarRepo]
  val svnRepo = Injector.inject[SvnDepRepo]
  val jiraRepo = Injector.inject[JiraRepos]


  "Controller tests- ExtraInfoController" should {

    "find Jira details by dep id" in {

      val Some(result) =route(app, FakeRequest(GET, s"/findJiraDetailsByDepId/${1}"))
      println("Result Add User:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      contentAsString(result) must include("JIRA Details  found")
    }

    "find SVN details by dep id" in {

      val Some(result) = route(app, FakeRequest(GET, s"/findSvnDetailsByDepId/${1}"))
      println("Result Add User:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      contentAsString(result) must include("SVN Details  found")
    }

    "find SONAR details by dep id" in {
      route(app, FakeRequest(GET, s"/findDeploymentById/${1}"))


      val Some(result) =route(app, FakeRequest(GET, s"/findSonarDetailsByDepId/${1}"))
      println("Result Add User:" + result)
      println(contentAsJson(result))
      status(result) mustEqual OK
      contentAsString(result) must include("SONAR Details  found")
    }

  }


}
