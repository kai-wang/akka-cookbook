package com.packt.chapter1

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import java.util.concurrent.ConcurrentLinkedQueue

import akka.dispatch._
import com.typesafe.config.Config

/**
  * Created by d771266 on 18/12/2017.
  */

class MyPriorityActor extends Actor {
  def receive: PartialFunction[Any, Unit] = {
    case x: Int => println(x)
    case x: String => println(x)
    case x: Double => println(x)
    case x => println(x)
  }
}

class MyPriorityActorMailBox(settings: ActorSystem.Settings, config: Config) extends UnboundedPriorityMailbox (
  PriorityGenerator {
    case x: Int => 1
    case x: String => 0
    case x: Double => 2
  })

object PriorityMailBox extends App{
  val actorSystem = ActorSystem("helloAkka")
  val myPriorityActor = actorSystem.actorOf(Props[MyPriorityActor].withDispatcher("prio-dispatcher"))

  myPriorityActor ! 6.0
  myPriorityActor ! 1
  myPriorityActor ! 5.0
  myPriorityActor ! 3
  myPriorityActor ! "Hello"
  myPriorityActor ! 5
  myPriorityActor ! "I am priority actor"
}
