package repositories

import entity.{Pending, TaskEntity, TaskType}

class TaskRepo(val dbProfile: DatabaseProfile) {

  import dbProfile.db.profile.api._

  implicit val typeTaskColumnType = MappedColumnType.base[TaskType, String](
    { `type` => TaskType.toStr(`type`) },
    { str => TaskType.toTaskType(str).getOrElse(Pending) }
  )

  class TaskTable(tag: Tag) extends Table[TaskEntity](tag, "Task") {
    def taskName = column[String]("name")

    def taskType = column[TaskType]("Type")

    def taskDescription = column[String]("description")

    def * =
      (taskName, taskType, taskDescription) <> ((TaskEntity.apply _).tupled, TaskEntity.unapply)
  }

  val taskTable = TableQuery[TaskTable]

  def createTable = {
    taskTable
      .schema
      .createIfNotExists
  }

  def getAll = {
    taskTable.result
  }

  def getTaskType(taskType: String) = {
    taskTable.filter(_.taskName === taskType)
      .result
  }

  def getTaskByName(taskName: String) = {
    taskTable
      .filter(_.taskName === taskName)
      .result
  }

  def insertTask(taskEntity: TaskEntity) = {
    taskTable += taskEntity

  }

  def updateTaskByName(taskName: String, taskEntity: TaskEntity) = {
    taskTable
      .filter(_.taskName === taskName)
      .update(taskEntity)
  }

  def deleteByName(taskName: String) = {
    taskTable
      .filter(_.taskName === taskName)
      .delete
  }

}

//object TaskType extends Enumeration {
//  val pending = Value("pending")
//  val complete = Value("complete")
//}