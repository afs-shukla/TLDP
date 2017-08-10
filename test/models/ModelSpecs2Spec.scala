package models

import models.{App1, AppsRepo}
import org.specs2.specification.AfterEach
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test._
import testhelpers.{EvolutionHelper, Injector}
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Await
import scala.concurrent.duration.Duration


class ModelSpecs2Spec extends PlaySpecification with AfterEach {

  val projectRepo = Injector.inject[AppsRepo]

  override def after = EvolutionHelper.clean()

  "An item " should {

    def app = GuiceApplicationBuilder()
      .build()

    "be inserted during the first test case" in new WithApplication(app) {
        val action = projectRepo.create( "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")
          .flatMap(_ => projectRepo.all)

        val result = Await.result(action, Duration.Inf)

        result must be_==(List(App1(1, "A","TestApp","TestDesc","Apptype","tomcat","ansible","Satish","GMOT","Magnu","http://localhost:3333/apprep","/home/abcd/playbooks/app123")))
    }

    "and not exist in the second test case" in new WithApplication(app) {
        val action = projectRepo.all

        val result = Await.result(action, Duration.Inf)

        result must be_==(List.empty)
    }


  }

}
