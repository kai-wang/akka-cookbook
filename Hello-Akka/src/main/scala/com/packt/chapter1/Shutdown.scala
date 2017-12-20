package com.packt.chapter1

import akka.actor.{PoisonPill, Props, ActorSystem, Actor}

/**
  * Created by d771266 on 18/12/2017.
  */

case object Stop

class ShutdownActor extends Actor{
  override def receive: Receive = {
    case msg:String => println(s"$msg")
    case Stop => context.stop(self)
  }
}

object Shutdown extends App{
  val actorSystem = ActorSystem("hello")
  val shutdownActor1 = actorSystem.actorOf((Props[ShutdownActor]), "shutdownActor1")
  shutdownActor1 ! "Hello"
  shutdownActor1 ! PoisonPill
  shutdownActor1 ! "how are you"

  val shutdownActor2 = actorSystem.actorOf(Props[ShutdownActor], "shutdownActor2")
  shutdownActor2 ! "hello"
  shutdownActor2 ! Stop
  shutdownActor2 ! "are you ok"

}
