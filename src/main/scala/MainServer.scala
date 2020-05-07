import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import facede.HelloFacade
import repositories.{DatabaseProfile, HelloRepo}
import routes.HelloRoute
import services.HelloService
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

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
    val facade = new HelloFacade(services)
    val routes = new HelloRoute(facade)

    for {
      _ <- facade.createTable
      http <- Http().bindAndHandle(routes.route, "0.0.0.0", 8080)
    } yield http

    println(s"service running on 8080")

  }
}
