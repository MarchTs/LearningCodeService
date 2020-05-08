import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import facede.{HelloFacade, TaskFacade}
import repositories.{DatabaseProfile, HelloRepo, TaskRepo}
import routes.{HelloRoute, TaskRoute}
import services.{HelloService, TaskService}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContextExecutor

object MainServer {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("example-http-server")
    implicit val dispatch: ExecutionContextExecutor = system.dispatcher

    implicit val db = new DatabaseProfile {
      override val db = DatabaseConfig.forConfig[JdbcProfile]("h2mem")
    }

    val helloRepo = new HelloRepo(db)
    val services = new HelloService(helloRepo)
    val helloFacade = new HelloFacade(services)
    val helloRoute = new HelloRoute(helloFacade)

    val taskRepo = new TaskRepo(db)
    val taskService = new TaskService(taskRepo)
    val taskFacade = new TaskFacade(taskService)
    val taskRoute = new TaskRoute(taskFacade)

    for {
      _ <- helloFacade.createTable
      _ <- taskFacade.createTable
      http <- Http().bindAndHandle(taskRoute.route ~ helloRoute.route, "0.0.0.0", 8080)
    } yield http

    println(s"service running on 8080")

  }
}
