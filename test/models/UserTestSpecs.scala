package models


import models.{TargetApps, AppsRepo}
import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play._
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import testhelpers.{EvolutionHelper, Injector}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by afssh on 13-08-2017.
  */
class UserTestSpecs extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach {

  val userRepo = Injector.inject[UsersRepo]



  override def afterEach() = EvolutionHelper.clean()

  "An item " should {

    "be inserted during the first test case" in  {
      val action = userRepo.create( "S0001","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
        .flatMap(_ => userRepo.all)

      val result = Await.result(action, Duration.Inf)

      result mustBe List(UsersDetail(1, "S0001","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com"))
    }

    "and not exist in the second test case" in  {
      val action = userRepo.all

      val result = Await.result(action, Duration.Inf)

      result mustBe List.empty
    }

    "and find the app test case" in {

      val action1 = userRepo.create( "S0001","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val result1 = Await.result(action1, Duration.Inf)
      println("Result1"+result1)
      val action2 = userRepo.create( "S0002","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val result2 = Await.result(action2, Duration.Inf)
      println("Result2"+result2)

      val action3 = userRepo.all

      val result = Await.result(action3, Duration.Inf)

      val action4=userRepo.findById(1)

      val result4=Await.result(action4,Duration.Inf)

      println( "find by id":+result4 )

      result.size mustBe 2



      // println("Result :"+result)
    }

    "and search application by applicatin id or application name or application description" in {

      val action1 = userRepo.create( "S0001","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val result1 = Await.result(action1, Duration.Inf)
      println("Result1"+result1)
      val action2 = userRepo.create( "S0002","Kum","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val result2 = Await.result(action2, Duration.Inf)
      println("Result2"+result2)

      val action3 = userRepo.create( "S0003","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val result3 = Await.result(action3, Duration.Inf)
      println("Resul32"+result3)

      val action4 = userRepo.create( "S0004","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val result4 = Await.result(action4, Duration.Inf)
      println("Result4"+result4)


      val action5 = userRepo.all

      val result5 = Await.result(action5, Duration.Inf)
      println("all the trades:"+result5)

      val action6=userRepo.appUserSearch(Some("S00"),Some("Ku"),None)

      val result6=Await.result(action6,Duration.Inf)

      result6.size mustBe 1


      val action7=userRepo.appUserSearch(Some("S"),None,None)

      val result7=Await.result(action7,Duration.Inf)

      result7.size mustBe 4
    }

    "and update the existing application row" in {
      val action1 = userRepo.create( "S0004","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val result1 = Await.result(action1, Duration.Inf)
      println("Result1"+result1)

      val action2=userRepo.updateUser(UsersDetail(result1, "S0001","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com"))
      val result2=Await.result(action2,Duration.Inf)

      println("Update Result:"+result2)

      val action3=userRepo.findById(result2)

      println("Get after update:"+Await.result(action3,Duration.Inf ))

      //result2 mustBe 1

    }




  }

}
