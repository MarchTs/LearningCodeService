package routes

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import facede.HelloFacade
import form.HelloPostForm
import io.circe.generic.auto._

import scala.util.{Failure, Success}

class HelloRoute(helloFacade: HelloFacade) extends FailFastCirceSupport {
  val route = pathPrefix("hello") {
    get {
      onComplete(helloFacade.getAll) {
        case Success(data) => complete(data)
        case Failure(err) => complete(500, err.getMessage)
      }
    } ~ (post & entity(as[HelloPostForm]) & path(Segment / "end")) { (form, id) =>
      onComplete(helloFacade.insert(form)){
        case Success(Right(value)) => complete(value)
        case Success(Left(err)) => complete(400, err)
        case Failure(err) => complete(500, err.getMessage)
      }
    }
  }
}
