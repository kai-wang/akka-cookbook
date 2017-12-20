package com.packt.chapter2
import akka.actor.{Actor, ActorSystem, Props, Terminated}
/**
  * Created by d771266 on 20/12/2017.
  */

case object Service
case object Kill

class ServiceActor extends Actor {
  override def receive: Receive = {
    case Service => println("I providea special service")
  }
}

class DeathWatchActor extends Actor {
  val child = context.actorOf(Props[ServiceActor], "serviceActor")
  context.watch(child)

  override def receive: Receive = {
    case Service => child ! Service
    case Kill => {
      context.stop(child)
    }
    case Terminated(`child`) => println("The service actor has termiated and no longer available")
  }
}
object DeathWatch extends App{
  val actorSystem = ActorSystem("Supervision")
  val deathWatchActor = actorSystem.actorOf(Props[DeathWatchActor])
  deathWatchActor ! Service
  deathWatchActor ! Service

  Thread.sleep(1000)
  deathWatchActor ! Kill
  deathWatchActor ! Service
}
