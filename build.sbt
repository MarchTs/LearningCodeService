name := "LearningCodeService"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.11"
libraryDependencies += "de.heikoseeberger" %% "akka-http-circe" % "1.31.0"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.3.2"
libraryDependencies += "com.zaxxer" % "HikariCP" % "3.4.1"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"
libraryDependencies += "com.h2database" % "h2" % "1.4.197"

val circeVersion = "0.12.3"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

// Test
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.11" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.6.3" % Test