package com.packt.chapter3

import akka.actor.{Props, ActorSystem, Actor}
import akka.routing.SmallestMailboxPool

/**
  * Created by d771266 on 20/12/2017.
  *
  * using akka.routing.SmallestMailboxPool to implemnt the SmallestMalboxPool of five actors
  */

class SmallestmailboxActor extends Actor {
  override def receive: Receive = {
    case msg: String => println(s"I am ${self.path.name}")
    case _ => println(s"I dont understand this message")
  }
}


object Smallestmailbox extends App{
  val actorSystem = ActorSystem("ehllo")
  val router = actorSystem.actorOf(SmallestMailboxPool(5).props(Props[SmallestmailboxActor]))

  (1 to 5).map(i => router ! s"hello $i")
}
