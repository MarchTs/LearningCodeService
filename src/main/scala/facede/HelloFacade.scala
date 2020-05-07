package facede

import entity.HelloEntity
import form.{HelloForm, HelloPostForm}
import message.HelloMessage
import services.HelloService

import scala.concurrent.ExecutionContext

class HelloFacade (helloService: HelloService)
                  (implicit ec : ExecutionContext){

  def createTable = {
    helloService.createTable
  }

  def getAll = {
    helloService.getAll.map(_.map(HelloEntity.toHelloMessage))

  }

  def insert(data: HelloPostForm) = {
    val dataEntity = HelloForm.toHelloEntity(data)
    helloService.insert(dataEntity).map(_.map(HelloEntity.toHelloMessage))
  }

}
