package com.packt.chapter3
import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.{BalancingPool, RoundRobinPool}
import com.packt.chapter3.Smallestmailbox.router

/**
  * Created by d771266 on 20/12/2017.
  */
class RoundRobinPoolActor extends Actor{
  override def receive: Receive = {
    case msg: String => println(s"I am ${self.path.name}")
    case _ => println(s"I dont understand this message")
  }
}

object RoundRobinPoolApp extends App {
  val actorSystem = ActorSystem("hello")
  val router = actorSystem.actorOf(RoundRobinPool(5).props(Props[RoundRobinPoolActor]))

  (1 to 6).map(i => router ! s"hello $i")
}