package form

import entity.{Pending, TaskEntity, TaskType}

trait TaskForm

case class TaskPostForm(name: String, taskType: String, description: String)
  extends TaskForm

object TaskForm {
  def toTaskEntity(taskPostForm: TaskPostForm): TaskEntity = {
    TaskEntity(
      taskName = taskPostForm.name,
      taskType = TaskType.toTaskType(taskPostForm.taskType).getOrElse(Pending),
      taskDescription = taskPostForm.description
    )
  }
}

