package entity

import cats.data.Ior.{Left, Right}

sealed trait TaskType

case object Pending extends TaskType
case object Complete extends TaskType

object TaskType {
  def toTaskType(`type`: String): Either[String, TaskType] = `type` match {
    case "pending" => Right(Pending)
    case "complete" => Right(Complete)
    case _ => Left("error")
  }

  def toStr(`type`: TaskType): String = `type` match {
    case Pending => "pending"
    case Complete => "complete"
  }
}
