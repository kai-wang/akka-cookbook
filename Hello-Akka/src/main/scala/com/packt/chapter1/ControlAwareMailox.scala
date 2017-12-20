package com.packt.chapter1

import akka.dispatch.ControlMessage
import akka.actor.{Props, Actor, ActorSystem}

/**
  * Created by d771266 on 18/12/2017.
  */

case object MyControlMessage extends ControlMessage

class Logger extends Actor {
  override def receive: Receive = {
    case MyControlMessage => println("oh, I have to process the contorl message firstly")
    case x => println(x)
  }
}
object ControlAwareMailox extends App{
  val actorSystem = ActorSystem("helloakka")
  val actor = actorSystem.actorOf(Props[Logger].withDispatcher("control-aware-disptacher"))

  actor ! "ello"
  actor ! "how are you"
  actor ! MyControlMessage
  actor ! "end"
}
