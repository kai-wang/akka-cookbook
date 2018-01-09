name := "Hello-Akka"

version := "1.0"

scalaVersion := "2.12.1"

/*
lazy val root = (project in file(".")).
  settings(
    name := "Hello-Akka",
    version := "1.0",
    scalaVersion := "2.12.1",
    mainClass in Compile := Some("com.packt.chatper5.Logging")
  )
*/

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.5.8",
  "org.scalatest" % "scalatest_2.12" % "3.0.3",
  "com.typesafe.akka" %% "akka-persistence" % "2.5.5",
  "org.iq80.leveldb"            % "leveldb"          % "0.9" ,
  "org.fusesource.leveldbjni"   % "leveldbjni-all"   % "1.8",
  "com.typesafe.akka" %% "akka-remote" % "2.5.8",
  "com.typesafe" % "config" % "1.3.2"
)

// META-INF discarding

/*
mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.first
  }
}
*/