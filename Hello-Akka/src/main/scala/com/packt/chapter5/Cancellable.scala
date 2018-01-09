package com.packt.chapter5

import akka.actor.{Cancellable, Props, ActorSystem, Actor}
import scala.concurrent.duration._

/**
  * Created by d771266 on 8/01/2018.
  */
class CancelOperation extends Actor{
  var i = 0

  override def receive: Receive = {
    case "tick" => {
      println(s"tick!!!")
      i = i - 1
      if(i == 0) Scheduler.cancellable.cancel()
    }
  }
}

object Scheduler extends App {
  val system = ActorSystem("Hello-Akka")
  import system.dispatcher
  val actor = system.actorOf(Props[CancelOperation])
  val cancellable: Cancellable = system.scheduler.schedule(0 seconds, 2 seconds, actor, "tick")
}
