package routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import entity.TaskEntity
import facede.TaskFacade
import form.TaskPostForm
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import repositories.{DatabaseProfile, TaskRepo}
import services.TaskService
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import io.circe.syntax._
import io.circe.generic.auto._

class TaskRouteTest
  extends AnyFlatSpec
  with Matchers
  with ScalatestRouteTest {

  implicit val db = new DatabaseProfile {
    override val db = DatabaseConfig.forConfig[JdbcProfile]("h2mem")
  }

  val taskRepo = new TaskRepo(db)
  val taskService = new TaskService(taskRepo)
  val taskFacade = new TaskFacade(taskService)
  val taskRoute = new TaskRoute(taskFacade)

  taskFacade.createTable()

  "[Get] /tasks" should "return 200 ok" in {
    Get("/tasks") ~> taskRoute.route ~> check {
      response.status should be(StatusCodes.OK)
    }
  }

  "[POST] /tasks" should "return 200 ok" in {
    val data = TaskPostForm(
      taskName = "SomeTask",
      taskType = "pending",
      taskDescription = "make for testing").asJson.noSpaces
    val entity = HttpEntity(ContentTypes.`application/json`, data)

    Post("/tasks", entity) ~> taskRoute.route ~> check {
      response.status should be(StatusCodes.OK)
    }
  }

  "[PUT] /tasks" should "return 200 ok" in {
    val dataBeforeUpdate = TaskPostForm(
      taskName = "SomeTask",
      taskType = "pending",
      taskDescription = "make for testing").asJson.noSpaces
    val entityBeforeUpdate = HttpEntity(ContentTypes.`application/json`, dataBeforeUpdate)
    val dataAfterUpdate = TaskPostForm(
      taskName = "SomeTask",
      taskType = "pending",
      taskDescription = "make for testing").asJson.noSpaces
    val entityAfterUpdate = HttpEntity(ContentTypes.`application/json`, dataAfterUpdate)

    Post("/tasks", entityBeforeUpdate) ~> taskRoute.route ~> check {
      response.status should be(StatusCodes.OK)
    }
    Get("/tasks") ~> taskRoute.route ~> check {
      response.status should be(StatusCodes.OK)
//      val stringResponse = responseAs[List[TaskPostForm]]
//      stringResponse shouldEqual(List(dataBeforeUpdate))
    }
    Put("/tasks/SomeTask", entityAfterUpdate) ~> taskRoute.route ~> check {
      responseAs[String] shouldEqual("1")
    }
  }

  "[DELETE] /tasks" should "return 200 ok" in {
    val dataBeforeUpdate = TaskPostForm(
      taskName = "SomeTask",
      taskType = "pending",
      taskDescription = "make for testing").asJson.noSpaces
    val entityBeforeUpdate = HttpEntity(ContentTypes.`application/json`, dataBeforeUpdate)

    Post("/tasks", entityBeforeUpdate) ~> taskRoute.route ~> check {
      response.status should be(StatusCodes.OK)
    }
    Delete("/tasks") ~> taskRoute.route ~> check {
      responseAs[String] shouldEqual("1")
    }
  }


}

