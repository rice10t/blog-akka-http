name := "blog-akka-http"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

val akkaVersion = "2.6.3"
val akkaHttpVersion = "10.1.11"
val circeVersion = "0.13.0"
libraryDependencies ++= Seq(
  // common
  "org.typelevel" %% "cats-core" % "2.0.0",
  "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided",
  // Akka
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "ch.megard" %% "akka-http-cors" % "0.4.2",
  "de.heikoseeberger" %% "akka-http-circe" % "1.31.0",
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
  // sttp
  "com.softwaremill.sttp.client" %% "core" % "2.0.0",
  "com.softwaremill.sttp.client" %% "async-http-client-backend-future" % "2.0.0",
  "com.softwaremill.sttp.client" %% "circe" % "2.0.0",
  // DB
  "org.postgresql" % "postgresql" % "9.4.1208",
  "io.getquill" %% "quill-jdbc" % "3.0.1",
  // Circe
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  // Testing
  "org.scalatest" %% "scalatest" % "3.0.8" % Test
)
