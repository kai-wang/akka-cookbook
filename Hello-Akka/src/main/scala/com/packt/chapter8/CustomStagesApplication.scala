package com.packt.chapter8
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

/**
  * Created by d771266 on 12/01/2018.
  */
object CustomStagesApplication extends App{

  implicit val actorSystem = ActorSystem("CustomeStage")
  implicit val materializer = ActorMaterializer()

  val source = Source.fromGraph(new HelloAkkaStreamsSource())

  val upperCaseMapper = Flow[String].map(_.toUpperCase())
  val splitter = Flow[String].mapConcat(_.split(" ").toList)

  val punctuationMapper = Flow[String].map(_.replaceAll("""
          [p{Punct}&&[^.]]""", "").replaceAll(
    System.lineSeparator(), ""))

  val filterEmptyElements = Flow[String].filter(_.nonEmpty)
  val wordCounterSink = Sink.fromGraph(new WordCounterSink())

  val stream = source
    .via(upperCaseMapper)
    .via(splitter)
    .via(punctuationMapper)
    .via(filterEmptyElements)
    .to(wordCounterSink)

  stream.run()

}
