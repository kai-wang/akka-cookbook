package com.packt.chapter2

import akka.actor.{ActorSystem, Actor, Props}

/**
  * Created by d771266 on 19/12/2017.
  */

case object CreateChild
case class Greet(msg: String)

class ChildActor extends Actor {
  override def receive: Receive = {
    case Greet(msg) => {
      println(s"My parent[${self.path.parent}] greeted to me [${self.path}] $msg")
    }
  }
}

class ParentActor extends Actor {
  override def receive: Receive = {
    case CreateChild => {
      val child = context.actorOf(Props[ChildActor], "child")
      child ! Greet("hello world")
    }
  }
}
object ParentChild extends App{
  val actorSystem = ActorSystem("superviser")
  val parent = actorSystem.actorOf(Props[ParentActor], "parent")
  parent !CreateChild
}
