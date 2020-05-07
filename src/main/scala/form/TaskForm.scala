package form

import entity.TaskEntity

trait TaskForm

case class TaskPostForm(
                         name: String,
                         taskType: String,
                         description: String)
  extends TaskForm

object TaskForm {
  def toTaskEntity(taskPostForm: TaskPostForm) = {
    TaskEntity(
      taskName = taskPostForm.name,
      taskType = taskPostForm.taskType,
      taskDescription = taskPostForm.description
    )
  }
}

