package com.packt.chapter8

import akka.actor.Actor
import com.packt.chapter8.SinkActor.{AckSinkActor, InitSinkActor}

/**
  * Created by d771266 on 12/01/2018.
  */
object SinkActor {
  case object CompletedSinkActor
  case object AckSinkActor
  case object InitSinkActor
}

class SinkActor extends Actor {
  def receive = {
    case InitSinkActor =>
      println("SinkActor initialized")
      sender ! AckSinkActor
    case something =>
      println(s"Received [$something] in SinkActor")
      sender ! AckSinkActor
  }
}