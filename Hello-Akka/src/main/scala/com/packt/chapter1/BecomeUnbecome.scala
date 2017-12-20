package com.packt.chapter1

import akka.actor.{Props, ActorSystem, Actor}
/**
  * Created by d771266 on 18/12/2017.
  */

class BecomeUnBecomeActor extends Actor {
  override def receive: Receive = {
    case true => context.become(isStateTrue)
    case false => context.become(isStateFalse)
    case _ => println("done know what you want to say")
  }

  def isStateTrue: Receive = {
    case msg: String => println(s"$msg")
    case false => context.become(isStateFalse)
  }

  def isStateFalse: Receive = {
    case msg: Int => println(s"$msg")
    case true => context.become(isStateTrue)
  }
}

object BecomeUnbecome extends App{
  val actorSystem = ActorSystem("hello")
  val becomeUnbecome = actorSystem.actorOf(Props[BecomeUnBecomeActor])

  becomeUnbecome ! true
  becomeUnbecome ! "hello"
  becomeUnbecome ! false
  becomeUnbecome ! 1000
  becomeUnbecome ! true
  becomeUnbecome ! "what do you do"
}
