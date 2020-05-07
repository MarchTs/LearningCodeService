package repositories

import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait DatabaseProfile {
  val db: DatabaseConfig[JdbcProfile]
}


