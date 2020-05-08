package entity

import message.TaskMessage

case class TaskEntity(taskName: String, taskType: TaskType, taskDescription: String)

object TaskEntity {
  def toTaskMessage(taskEntity: TaskEntity): TaskMessage = {
    TaskMessage(
      taskName = taskEntity.taskName,
      taskType = TaskType.toStr(taskEntity.taskType),
      taskDescription = taskEntity.taskDescription)
  }
}
