package models

import com.typesafe.scalalogging.LazyLogging
import models.{TargetApps, AppsRepo}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import testhelpers.{EvolutionHelper, Injector}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class AppScalaTestSpec extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach with LazyLogging {

  val projectRepo = Injector.inject[AppsRepo]

  override def afterEach() = EvolutionHelper.clean()

  "An item " should {

    "be inserted during the first test case" in  {
      val action = projectRepo.create( "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
        .flatMap(_ => projectRepo.all)

      val result = Await.result(action, Duration.Inf)

      result mustBe List(TargetApps(1, "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123"))
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

      val action4=projectRepo.findById(1)

      val result4=Await.result(action4,Duration.Inf)

     println( "find by id":+result4 )

      result.size mustBe 2



     // println("Result :"+result)
    }

    "and search application by applicatin id or application name or application description" in {

      val action1 = projectRepo.create( "appid1","niagara","fall in canada","strategic type","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
      val result1 = Await.result(action1, Duration.Inf)
      println("Result1"+result1)
      val action2 = projectRepo.create( "appid2","figuration","getting rade card from cesium","classic type","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
      val result2 = Await.result(action2, Duration.Inf)
      println("Result2"+result2)

      val action3 = projectRepo.create( "appid3","bookingtrade","books the trades in strategic","booking applicaiton type","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
      val result3 = Await.result(action3, Duration.Inf)
      println("Resul32"+result3)

      val action4 = projectRepo.create( "appid4","jetengine","Clinet side confirmation","Flying machine type","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
      val result4 = Await.result(action4, Duration.Inf)
      println("Result4"+result4)


      val action5 = projectRepo.all

      val result5 = Await.result(action5, Duration.Inf)
      println("all the trades:"+result5)

      val action6=projectRepo.appSearch(Some("app"),Some("figu"),None)

      val result6=Await.result(action6,Duration.Inf)

      result6.size mustBe 1


      val action7=projectRepo.appSearch(Some("app"),None,None)

      val result7=Await.result(action7,Duration.Inf)

      result7.size mustBe 4
    }

    "and update the existing application row" in {
      val action1 = projectRepo.create( "appid1","niagara","fall in canada","strategic type","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
      val result1 = Await.result(action1, Duration.Inf)
      println("Result1"+result1)

      val action2=projectRepo.updateApp(TargetApps(result1,"appid111","niagara11","fall in canada111","strategic type","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123"))
      val result2=Await.result(action2,Duration.Inf)

      println("Update Result:"+result2)

      val action3=projectRepo.findById(result2)

      println("Get after update:"+Await.result(action3,Duration.Inf ))

      //result2 mustBe 1

    }
    "removed apps " in  {
      val action = projectRepo.create( "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
        .flatMap(_ => projectRepo.all)

      val result = Await.result(action, Duration.Inf)

      result mustBe List(TargetApps(1, "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123"))

      val action2=projectRepo.delete(1)

      val result2 = Await.result(action2, Duration.Inf)

      logger.info("Removed AppDetails:"+result2)

    }

    "find by id apps " in  {
      val action = projectRepo.create( "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
        .flatMap(_ => projectRepo.all)

      val result = Await.result(action, Duration.Inf)

      result mustBe List(TargetApps(1, "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123"))

      val action2=projectRepo.findById(1)

      val result2 = Await.result(action2, Duration.Inf)

      logger.info("Applciation found for id :1"+result2)

    }





  }

}
