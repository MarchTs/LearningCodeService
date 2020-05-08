package routes

import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import _root_.entity.TaskEntity
import facede.TaskFacade
import form.TaskPostForm
import io.circe.generic.auto._

import scala.util.{Failure, Success}

class TaskRoute(taskFacade: TaskFacade) extends FailFastCirceSupport {
  val route = pathPrefix("tasks") {
    get {
      onComplete(taskFacade.getAll()) {
        case Success(data) => complete(data)
        case Failure(error) => complete(500, error)
      }
    } ~ (post & entity(as[TaskPostForm])) { (form) =>
      onComplete(taskFacade.insert(form)) {
        case Success(value) => complete(value)
        case Failure(error) => complete(500, error)
      }
    } ~ (put & entity(as[TaskPostForm]) & path(Segment)) { (form, id) =>
      onComplete(taskFacade.update(id, form)){
        case Success(value) => complete(value)
        case Failure(error) => complete(500, error)
      }
    } ~ (delete & path(Segment)){(id) =>
      onComplete(taskFacade.delete(id)){
        case Success(value) => complete(value)
        case Failure(error) => complete(500, error)
      }
    }
  }
}



