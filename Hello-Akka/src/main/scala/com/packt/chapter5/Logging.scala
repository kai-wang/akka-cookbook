package com.packt.chapter5

import akka.actor.{Props, ActorSystem, ActorLogging, Actor}

/**
  * Created by d771266 on 8/01/2018.
  */

class LoggingActor extends Actor with ActorLogging {
  override def receive: Receive = {
    case (a: Int, b: Int) => {
      log.info(s"sum of $a and $b is ${a+b}")
    }
    case msg => log.warning(s"warinig: $msg" )
  }
}

object Logging extends App{
  val system = ActorSystem("hello")
  val actor = system.actorOf(Props[LoggingActor])

  actor !(10, 12)
  actor !"Hello"

  system.terminate()

}
