package models

import java.util.Date

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import testhelpers.{EvolutionHelper, Injector}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by afssh on 14-08-2017.
  */
class SonarScalaTestSpecs extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach with LazyLogging {

  val deployRepo = Injector.inject[DeployRepo]
  val usrRepo = Injector.inject[UsersRepo]
  val appRepo = Injector.inject[AppsRepo]
  val svnRepo = Injector.inject[SvnDepRepo]
  val jiraRepo = Injector.inject[JiraRepos]
  val sonarRepo = Injector.inject[SonarRepo]

  override def afterEach() = EvolutionHelper.clean()

  "Sonar specs  " should {

    "Insert New Rows in Sonar Details" in {

      val action2 = appRepo.create("A002", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123")

      val app2 = Await.result(action2, Duration.Inf)

      val action3 = appRepo.create("A003", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123")
      val app3 = Await.result(action3, Duration.Inf)

      val user1 = usrRepo.create("S0001", "Satish", "Shukla", "Apptype", "tomcat", "ansible", "32454353453", "afs.shukla@gmail.com")
      val ur1 = Await.result(user1, Duration.Inf)

      val user2 = usrRepo.create("S0002", "Kum", "Shukla", "Apptype", "tomcat", "ansible", "32454353453", "afs.shukla@gmail.com")
      val ur2 = Await.result(user2, Duration.Inf)

      val deploy1 = Await.result(deployRepo.create("DEP001", ur1, app2, new java.sql.Timestamp(new Date().getTime), "SIT", "SUCCESS", "Dummy Deployment"), Duration.Inf)
      val deploy2 = Await.result(deployRepo.create("DEP002", ur2, app3, new java.sql.Timestamp(new Date().getTime), "UAT", "SUCCESS", "Dummy Deployment"), Duration.Inf)
      println("SIT Deployment:" + deploy1)
      println("UAT deployment:" + deploy2)

      // logger.info("Svn Details 1" + Await.result(svnRepo.create(deploy1, "hellowolrd.java", "10.20.34", "4094"), Duration.Inf))
      // logger.info("Svn Details 2" + Await.result(svnRepo.create(deploy2, "FindUser.java", "10.20.35", "4095"), Duration.Inf))
      val sonar1 = Await.result(sonarRepo.create(deploy1, "SONAR-0320", 1, 4, 10, "10-05-2015"), Duration.Inf)
      /*      val jira2=Await.result(sonarRepo.create(deploy1, "SONAR-0321", "Creatign GID", "Completed","10.44.22"), Duration.Inf)
      val jira3=Await.result(sonarRepo.create(deploy1, "SONAR-0322", "Correcting SSI Details", "Completed","10.44.22"), Duration.Inf)
      val jira4=Await.result(sonarRepo.create(deploy1, "SONAR-0323", "Adding new account number for me", "Completed","10.44.22"), Duration.Inf)*/
      val allSonar = Await.result(sonarRepo.findSonarDetailsByDeplId(deploy1), Duration.Inf)
      logger.info("JIRA Details =======:" + allSonar)

      allSonar.size mustBe 1

    }
  }
}
