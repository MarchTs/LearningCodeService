package routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import facede.HelloFacade
import form.HelloPostForm
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import io.circe.syntax._
import io.circe.generic.auto._
import repositories.{DatabaseProfile, HelloRepo}
import services.HelloService
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

class HelloRouteTest
  extends AnyFlatSpec
    with Matchers
    with ScalatestRouteTest {
  implicit val db = new DatabaseProfile {
    override val db = DatabaseConfig.forConfig[JdbcProfile]("h2mem")
  }

  val helloRepo = new HelloRepo(db)
  val services = new HelloService(helloRepo)
  val facade = new HelloFacade(services)
  val routes = new HelloRoute(facade)

  "[Get] ~/hello route" should "retuen 200 ok" in {
    Get("/hello") ~> routes.route ~> check {
      response.status should be(StatusCodes.OK)
    }
  }

  "[Post] ~/hello route" should "retuen 200 ok" in {
    val data = HelloPostForm("some one", "something@use.ful").asJson.noSpaces
    val entity = HttpEntity(ContentTypes.`application/json`, data)

    Post("/hello/someSegment/end", entity) ~> routes.route ~> check {
      response.status should be(StatusCodes.OK)
    }
  }
}
