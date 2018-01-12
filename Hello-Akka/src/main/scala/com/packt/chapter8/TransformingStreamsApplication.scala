package com.packt.chapter8

import java.nio.file.Paths
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._



/**
  * Created by d771266 on 12/01/2018.
  */
object TransformingStreamsApplication extends App{

  implicit val actorSystem = ActorSystem("TransformingStream")
  implicit val actorMaterializer = ActorMaterializer()

  val MaxGroups = 100

  val path = Paths.get("src/main/resources/zipped-file.gz")

  val stream = FileIO.fromPath(path)
    .via(Compression.gunzip())
    .map(_.utf8String.toUpperCase)
    .mapConcat(_.split(" ").toList)
    .collect {
      case w if w.nonEmpty => w.replaceAll(""""[p{Punct}&&[^.]]""",
        "").replaceAll(System.lineSeparator(), "")
    }
    .groupBy(MaxGroups, identity)
    .map(_-> 1)
    .reduce((l, r) => (l._1, l._2 + r._2))
    .mergeSubstreams
    .to(Sink.foreach(println))

  stream.run()

}
