package facade

import akka.http.scaladsl.testkit.ScalatestRouteTest
import entity.TaskEntity
import facede.TaskFacade
import form.TaskPostForm
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.matchers.should.Matchers
import repositories.{DatabaseProfile, TaskRepo}
import routes.TaskRoute
import services.TaskService
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

class TaskFacadeTest extends AnyFlatSpec
  with Matchers
  with ScalatestRouteTest {
  implicit val db = new DatabaseProfile {
    override val db = DatabaseConfig.forConfig[JdbcProfile]("h2mem")
  }

  val taskRepo = new TaskRepo(db)
  val taskService = new TaskService(taskRepo){
    override def insert(taskEntity: TaskEntity): Future[Either[String, TaskEntity]] ={
     Future.successful(Right(taskEntity))
    }

    override def update(name: String, taskEntity: TaskEntity): Future[Int] = {
      Future.successful(1)
    }

    override def delete(name: String): Future[Int] = {
      Future.successful(1)
    }
  }
  val taskFacade = new TaskFacade(taskService)

  "Best case scenario everything is perfect insert" should "success" in {
    val taskPostForm = TaskPostForm("sometask", "complete", "this is testing task")
    taskFacade.insert(taskPostForm).map(_.isRight should be(true))
  }

  "Best case scenario everything is perfect update" should "success" in {
    val taskPostForm = TaskPostForm("sometask", "complete", "this is testing task")
    taskFacade.update("the old task", taskPostForm).map(_.toString shouldEqual (1))
  }
}
