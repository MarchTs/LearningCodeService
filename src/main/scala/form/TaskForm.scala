package form

import entity.TaskEntity

trait TaskForm

case class TaskPostForm(
                         taskName: String,
                         taskType: String,
                         taskDescription: String)
  extends TaskForm

object TaskForm {
  def toTaskEntity(taskPostForm: TaskPostForm): TaskEntity = {
    TaskEntity(
      taskName = taskPostForm.taskName,
      taskType = taskPostForm.taskType,
      taskDescription = taskPostForm.taskDescription
    )
  }
}

