package facede

import entity.TaskEntity
import form.{TaskForm, TaskPostForm}
import services.TaskService

import scala.concurrent.ExecutionContext

class TaskFacade (taskService: TaskService)
                 (implicit ec: ExecutionContext){

  def createTable() ={
    taskService.createTable()
  }

  def getAll() = {
    taskService.getAll
  }

  def insert(taskPostForm: TaskPostForm) = {
    val taskEntity = TaskForm.toTaskEntity(taskPostForm)
    taskService.insert(taskEntity)
  }

  def delete(name: String) = {
    taskService.delete(name)
  }

  def update(name: String , taskPostForm: TaskPostForm) = {
    val taskEntity = TaskForm.toTaskEntity(taskPostForm)
    taskService.update(name, taskEntity)
  }

}
