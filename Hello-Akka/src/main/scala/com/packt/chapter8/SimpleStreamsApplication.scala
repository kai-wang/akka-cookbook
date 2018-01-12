package com.packt.chapter8

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

/**
  * Created by d771266 on 12/01/2018.
  */
object SimpleStreamsApplication extends App{

  implicit val actorSystem = ActorSystem("SimpleStream")
  implicit val actorMaterializer = ActorMaterializer()

  val fileList = List("src/main/resources/application.conf",
    "src/main/resources/application-1.conf",
    "src/main/resources/application-2.conf"
  )

  val stream = Source(fileList)
    .map(new java.io.File(_))
    .filter(_.exists())
    .filter((_.length() != 0))
    .to(Sink.foreach(f => println(s"Absolute Path: ${f.getAbsolutePath}")))

  stream.run()
}
