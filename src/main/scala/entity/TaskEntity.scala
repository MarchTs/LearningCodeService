package entity

import message.TaskMessage

case class TaskEntity(taskName: String, taskType: String, taskDescription: String)

object TaskEntity {
  def toTaskMessage(taskEntity: TaskEntity): TaskMessage = {
    TaskMessage(
      taskName = taskEntity.taskName,
      taskType = taskEntity.taskType,
      taskDescription = taskEntity.taskDescription)
  }
}
