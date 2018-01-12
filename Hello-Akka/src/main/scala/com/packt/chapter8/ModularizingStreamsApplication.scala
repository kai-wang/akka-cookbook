package com.packt.chapter8

import java.nio.file.Paths
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString


/**
  * Created by d771266 on 12/01/2018.
  */
object ModularizingStreamsApplication extends App {
  implicit val actorSystem = ActorSystem("TransoformingStream")
  implicit val materializer = ActorMaterializer()

  val MaxGroups = 100

  val path = Paths.get("src/main/resources/zipped-file.gz")
  val source = FileIO.fromPath(path)

  val gunzip = Flow[ByteString].via(Compression.gunzip())
  val utf8UppercaseMapper = Flow[ByteString].map(_.utf8String.toUpperCase)
  val utf8LowercaseMapper = Flow[ByteString].map(_.utf8String.toLowerCase)
  val splitter = Flow[String].mapConcat(_.split(" ").toList)

  val punctuationMapper = Flow[String].map(_.replaceAll("""
            [p{Punct}&&[^.]]""", "").replaceAll(
    System.lineSeparator(), ""))

  val filterEmptyElements = Flow[String].filter(_.nonEmpty)
  val wordCountFlow = Flow[String]
    .groupBy(MaxGroups, identity)
    .map(_ -> 1)
    .reduce((l, r) => (l._1, l._2 + r._2))
    .mergeSubstreams

  val printlnSink = Sink.foreach(println)

  val streamUppercase = source
    .via(gunzip)
    .via(utf8UppercaseMapper)
    .via(splitter)
    .via(punctuationMapper)
    .via(filterEmptyElements)
    .via(wordCountFlow)
    .to(printlnSink)
  val streamLowercase = source
    .via(gunzip)
    .via(utf8LowercaseMapper)
    .via(splitter)
    .via(punctuationMapper)
    .via(filterEmptyElements)
    .via(wordCountFlow)
    .to(printlnSink)

  streamUppercase.run()
  streamLowercase.run()

}
