package form

import entity.HelloEntity

trait HelloForm

case class HelloPostForm(name: String, email: String) extends HelloForm

object HelloForm {
  def toHelloEntity(data: HelloPostForm) = {
    HelloEntity(
      name = data.name,
      email = data.email
    )
  }
}