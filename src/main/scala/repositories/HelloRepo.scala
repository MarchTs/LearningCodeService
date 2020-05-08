package repositories

import entity.HelloEntity

class HelloRepo(val dbProfile: DatabaseProfile) {

  import dbProfile.db.profile.api._

  class HelloTable(tag: Tag) extends Table[HelloEntity](tag, "tableHello") {
    def name = column[String]("name")

    def email = column[String]("email")

    def * = (name, email) <> ((HelloEntity.apply _).tupled, HelloEntity.unapply)

  }

  val tableHello = TableQuery[HelloTable]

  def createTable = {
    tableHello
      .schema
      .createIfNotExists
  }

  def getAll = {
    tableHello.result
  }

  def getByName(name: String) = {
    tableHello
      .filter(_.name === name)
      .result
  }

  def insertData(data: HelloEntity) = {
    tableHello += data
  }

  def deleteByName(name: String) = {
    tableHello
      .filter(_.name === name)
      .delete
  }

  def updateByName(name: String, data: HelloEntity) = {
    tableHello
      .filter(_.name === name)
      .update(data)
  }

}
