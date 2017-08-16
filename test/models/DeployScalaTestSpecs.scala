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
class DeployScalaTestSpecs extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach with LazyLogging{

  val deployRepo = Injector.inject[DeployRepo]
  val userRepo = Injector.inject[UsersRepo]
  val appRepo = Injector.inject[AppsRepo]

  override def afterEach() = EvolutionHelper.clean()

  "An item " should {

    "Insert New Rows in " in {

      val action2 = appRepo.create( "A002","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")

      val app2=Await.result(action2, Duration.Inf)

      val action3 = appRepo.create( "A003","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
      val app3=Await.result(action3, Duration.Inf)



      val user1 = userRepo.create( "S0001","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val ur1= Await.result(user1, Duration.Inf)

      val user2 = userRepo.create( "S0002","Kum","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val ur2= Await.result(user2, Duration.Inf)


      val deploy1=Await.result(deployRepo.create("DEP001",ur1,app2,new java.sql.Timestamp(new Date().getTime),"SIT","SUCCESS","Dummy Deployment"),Duration.Inf)
      val deploy2=Await.result(deployRepo.create("DEP002",ur2,app3,new java.sql.Timestamp(new Date().getTime),"UAT","SUCCESS","Dummy Deployment"),Duration.Inf)
      println("SIT Deployment:"+deploy1)
      println("UAT deployment:"+deploy2)

      logger.info("Find By app id"+Await.result(deployRepo.findByAppId(app3),Duration.Inf))
      val dep=Await.result(deployRepo.findById(deploy1),Duration.Inf)
       dep match  {
            case Some(s) => logger.info("Find User"+Await.result(userRepo.findById(s.userId),Duration.Inf))
         }
      logger.info("Find Deploy"+dep)


    }

  }

}
