name := "Hello-Akka"

version := "1.0"

scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.8" % Test
)