package models

import org.scalatest.BeforeAndAfterEach
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import testhelpers.{EvolutionHelper, Injector}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by afssh on 14-08-2017.
  */
class AppUserScalaTestSpecs extends PlaySpec with GuiceOneAppPerTest  with BeforeAndAfterEach {

  val userAppRepo = Injector.inject[UserAppRepo]
  val userRepo = Injector.inject[UsersRepo]
  val appRepo = Injector.inject[AppsRepo]

  override def afterEach() = EvolutionHelper.clean()

  "an item " should {

    "be inserted during the first test case" in  {

      println("Begining ALLLL !!! +"+Await.result(userAppRepo.all, Duration.Inf))
      val action1 = appRepo.create( "A001","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")

       val app1=Await.result(action1, Duration.Inf)

      val action2 = appRepo.create( "A002","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")

       val app2=Await.result(action2, Duration.Inf)

      val action3 = appRepo.create( "A003","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
        val app3=Await.result(action3, Duration.Inf)



      val user1 = userRepo.create( "S0001","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val ur1= Await.result(user1, Duration.Inf)

      val user2 = userRepo.create( "S0002","Kum","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val ur2= Await.result(user2, Duration.Inf)

      val user3 = userRepo.create( "S0003","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val ur3=Await.result(user3, Duration.Inf)


      val user4 = userRepo.create( "S0004","Satish","Shukla","Apptype","tomcat","ansible","32454353453","afs.shukla@gmail.com")
      val ur4=Await.result(user4, Duration.Inf)

      val appUserAction1 = userAppRepo.create(ur1,app1 )
      val appUserAction2 = userAppRepo.create(ur1,app2 )
      val appUserAction3 = userAppRepo.create(ur2,app3 )
      val appUserAction4 = userAppRepo.create(ur2,app1 )
      val appUserAction5 = userAppRepo.create(ur3,app2 )
      val appUserAction6 = userAppRepo.create(ur3,app1 )

      println("Begining ALLLL Down !!! +"+Await.result(userAppRepo.all, Duration.Inf))

      Await.result(appUserAction1, Duration.Inf)
      Await.result(appUserAction2, Duration.Inf)
    Await.result(appUserAction3, Duration.Inf)
    Await.result(appUserAction4, Duration.Inf)
    Await.result(appUserAction5, Duration.Inf)
    Await.result(appUserAction6, Duration.Inf)

      val findUserForApp=Await.result(userAppRepo.findAllUsersByAppId(app2),Duration.Inf)

      println("findUserForApp"+findUserForApp)


      val findAppByUserId=Await.result(userAppRepo.findAllAppsByUserId(ur1),Duration.Inf)

      println("findAppByUserId!!!"+findAppByUserId)


      val delete=Await.result(userAppRepo.delete(UserApp(1,1)),Duration.Inf)

      val findAppByUserIdPostDelete=Await.result(userAppRepo.findAllAppsByUserId(ur1),Duration.Inf)

      println("findAppByUserId!!Post Delete"+findAppByUserIdPostDelete)

    }


  }

}
