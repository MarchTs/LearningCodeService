package entity

import message.HelloMessage

case class HelloEntity(name: String, email: String)

object HelloEntity {
  def toHelloMessage(data: HelloEntity) = {
    HelloMessage(name = data.name, email = data.email)
  }
}