package models

import models.{App1, AppsRepo}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import testhelpers.{EvolutionHelper, Injector}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class ModelScalaTestSpec extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach {

  val projectRepo = Injector.inject[AppsRepo]

  override def afterEach() = EvolutionHelper.clean()

  "An item " should {

    "be inserted during the first test case" in  {
        val action = projectRepo.create( "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
          .flatMap(_ => projectRepo.all)

        val result = Await.result(action, Duration.Inf)

        result mustBe List(App1(1, "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123"))
    }

    "and not exist in the second test case" in  {
        val action = projectRepo.all

        val result = Await.result(action, Duration.Inf)

        result mustBe List.empty
    }

    "and find the app test case" in {

      val action1 = projectRepo.create( "Action1","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
      val result1 = Await.result(action1, Duration.Inf)
      println("Result1"+result1)
      val action2 = projectRepo.create( "Action2","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
      val result2 = Await.result(action2, Duration.Inf)
      println("Result2"+result2)

      val action3 = projectRepo.all

      val result = Await.result(action3, Duration.Inf)

      println("Result :"+result)




    }


  }

}
