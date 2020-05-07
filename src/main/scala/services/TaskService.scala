package services

import entity.TaskEntity
import repositories.{DatabaseProfile, HelloRepo, TaskRepo}

import scala.concurrent.{ExecutionContext, Future}

class TaskService (taskRepo: TaskRepo)
                  (implicit dbProfile: DatabaseProfile,
                   ec: ExecutionContext) {
  def createTable() ={
    dbProfile
      .db.db
      .run(taskRepo.createTable)
  }

  def getAll: Future[List[TaskEntity]] = {
    dbProfile
      .db.db
      .run(taskRepo.getAll)
      .map(_.toList)
  }

  def insert(taskEntity: TaskEntity) = {
    dbProfile
      .db.db
      .run(taskRepo.insertTask(taskEntity))
      .map { row =>
        if(row == 1) Right(taskEntity)
        else Left("Something went wrong on insert task")
      }
  }

  def delete(name: String) = {
    dbProfile
      .db.db
      .run(taskRepo.deleteByName(name))
  }

  def update(name: String , taskEntity: TaskEntity) = {
    dbProfile
      .db.db
      .run(taskRepo.updateTaskByName(name, taskEntity))
  }

}
