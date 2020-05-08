package facade

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import entity.HelloEntity
import facede.HelloFacade
import form.HelloPostForm
import message.HelloMessage
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import repositories.{DatabaseProfile, HelloRepo}
import routes.HelloRoute
import services.HelloService
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.Future
import scala.util.Failure

class facadeTest extends AnyFlatSpec
  with Matchers
  with ScalatestRouteTest {
  implicit val db = new DatabaseProfile {
    override val db = DatabaseConfig.forConfig[JdbcProfile]("h2mem")
  }

  val helloRepo = new HelloRepo(db)
  val successfulService = new HelloService(helloRepo) {
    override def insert(data: HelloEntity): Future[Either[String, HelloEntity]] = Future.successful(Right(data))
  }
  val insertFail = new HelloService(helloRepo) {
    override def insert(data: HelloEntity): Future[Either[String, HelloEntity]] = Future.successful(Left("Insert fail for some reason"))
  }

  val networkFail = new HelloService(helloRepo) {
    override def insert(data: HelloEntity): Future[Either[String, HelloEntity]] = Future.failed(new Exception("Testing network fail"))
  }
  val bastCaseFacade = new HelloFacade(successfulService)
  val worseCaseFacade = new HelloFacade(networkFail)


  "insert" should "success" in {
    bastCaseFacade.insert(HelloPostForm("somename", "someemail@mail.com")).map(_.isRight should be(true))
  }

  "case Fail " should "match" in {
    worseCaseFacade.insert(HelloPostForm("somename", "someemail@mail.com")).onComplete{
      case Failure(exception) => exception should be("Testing network fail")
    }
  }
}

