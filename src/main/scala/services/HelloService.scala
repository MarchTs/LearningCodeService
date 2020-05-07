package services

import entity.HelloEntity
import repositories.{DatabaseProfile, HelloRepo}

import scala.concurrent.{ExecutionContext, Future}


class HelloService(helloRepo: HelloRepo)
                  (implicit dbProfile: DatabaseProfile,
                   ec: ExecutionContext) {
  def createTable(): Future[Unit] = {
    dbProfile
      .db.db
      .run(helloRepo.createTable)
  }

  def getAll: Future[List[HelloEntity]] = {
    dbProfile
      .db.db
      .run(helloRepo.getAll)
      .map(_.toList)
  }

  def insert(data: HelloEntity): Future[Either[String, HelloEntity]] = {
    dbProfile
      .db.db
      .run(helloRepo.insertData(data))
      .map { row =>
        if (row == 1) Right(data)
        else Left("Cannot Insert")
      }
  }

}
