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
  * Created by afssh on 02-09-2017.
  */
class ReleaseNoteTestSpecs extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach with LazyLogging {

  val deployRepo = Injector.inject[DeployRepo]
  val usrRepo = Injector.inject[UsersRepo]
  val appRepo = Injector.inject[AppsRepo]
  val svnRepo = Injector.inject[SvnDepRepo]
  val jiraRepo=Injector.inject[JiraRepos]
  val relRepo=Injector.inject[ReleaseNotesRepo]

  override def afterEach() = EvolutionHelper.clean()

  "A Release Note " should {

    val action2 = appRepo.create("A002", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123")

    val app2 = Await.result(action2, Duration.Inf)

    val action3 = appRepo.create("A003", "TestApp", "TestDesc", "Apptype", "tomcat", "ansible", "Satish", "GMOT", "Magnu", "http://localhost:3333/apprep", "/home/abcd/playbooks/app123")
    val app3 = Await.result(action3, Duration.Inf)

    val user1 = usrRepo.create("S0001", "Satish", "Shukla", "Apptype", "tomcat", "ansible", "32454353453", "afs.shukla@gmail.com")
    val ur1 = Await.result(user1, Duration.Inf)

    val user2 = usrRepo.create("S0002", "Kum", "Shukla", "Apptype", "tomcat", "ansible", "32454353453", "afs.shukla@gmail.com")
    val ur2 = Await.result(user2, Duration.Inf)

    val deploy1 = Await.result(deployRepo.create("DEP001", ur1, app2, new java.sql.Timestamp(new Date().getTime), "SIT", "SUCCESS", "Dummy Deployment","fixVersion"), Duration.Inf)
    val deploy2 = Await.result(deployRepo.create("DEP002", ur2, app3, new java.sql.Timestamp(new Date().getTime), "UAT", "SUCCESS", "Dummy Deployment","fixVersion"), Duration.Inf)
    println("SIT Deployment:" + deploy1)
    println("UAT deployment:" + deploy2)

  /*  "of type release details Be Inserted " in {
     val relInsertAction=relRepo.create(deploy1,"Release Details", "Release for UAT","This is dummy release data, Actual functionality comming soon",new java.sql.Timestamp(new Date().getTime))
      val releaseNote = Await.result(relInsertAction, Duration.Inf)
      releaseNote mustBe (1)
    }

    "of type Runner Book Be Inserted " in {
      val relInsertAction=relRepo.create(deploy1,"RUN BOOK", "Release for UAT","This is dummy release data, Actual functionality comming soon",new java.sql.Timestamp(new Date().getTime))
      val releaseNote = Await.result(relInsertAction, Duration.Inf)
      releaseNote mustBe (1)
    }
*/
 /*   " fetched by Deployment id" in {
      val relInsertAction1=relRepo.create(deploy1,"RUN BOOK", "Release for UAT","This is dummy release data, Actual functionality comming soon",new java.sql.Timestamp(new Date().getTime))
      val releaseNote1 = Await.result(relInsertAction1, Duration.Inf)
      releaseNote1 mustBe (1)

      val relInsertAction=relRepo.create(deploy1,"RUN BOOK", "Release for UAT","This is dummy release data, Actual functionality comming soon",new java.sql.Timestamp(new Date().getTime))
      val releaseNote = Await.result(relInsertAction, Duration.Inf)
      releaseNote mustBe (2)


      val fetchByDepId=Await.result(relRepo.findByDepId(deploy1), Duration.Inf)

      fetchByDepId.size mustBe (2)
        println("Release Notes:" + fetchByDepId)

    }*/

   /* " fetched by Deployment id and release note type" in {
      val relInsertAction1=relRepo.create(deploy1,"RUN BOOK", "Release for UAT","This is dummy release data, Actual functionality comming soon",new java.sql.Timestamp(new Date().getTime))
      val releaseNote1 = Await.result(relInsertAction1, Duration.Inf)
      releaseNote1 mustBe (1)

      val relInsertAction=relRepo.create(deploy1,"RUN BOOK", "Release for UAT","This is dummy release data, Actual functionality comming soon",new java.sql.Timestamp(new Date().getTime))
      val releaseNote = Await.result(relInsertAction, Duration.Inf)
      releaseNote mustBe (2)


      val fetchByDepId=Await.result(relRepo.findByDepIdAndRelType(deploy1,"RUN BOOK"), Duration.Inf)

      //fetchByDepId.size mustBe (2)
      println("Release Notes:" + fetchByDepId)

    }*/

    " fetched by depID id and release note type" in {
      val relInsertAction1=relRepo.create(deploy1,"RUN BOOK", "Release for UAT","This is dummy release data, Actual functionality comming soon",new java.sql.Timestamp(new Date().getTime))
      val releaseNote1 = Await.result(relInsertAction1, Duration.Inf)
      releaseNote1 mustBe (1)

      val relInsertAction=relRepo.create(deploy1,"RUN BOOK", "Release for UAT","This is dummy release data, Actual functionality comming soon",new java.sql.Timestamp(new Date().getTime))
      val releaseNote = Await.result(relInsertAction, Duration.Inf)
      releaseNote mustBe (2)


      val fetchByDepId=Await.result(relRepo.findByDepId(deploy1), Duration.Inf)

      //fetchByDepId.size mustBe (2)
      println("Release Notes:" + fetchByDepId)

    }

  }

}
