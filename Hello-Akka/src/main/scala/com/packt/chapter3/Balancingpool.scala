package com.packt.chapter3
import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.{BalancingPool, RoundRobinPool}
import com.packt.chapter3.Smallestmailbox.router

/**
  * Created by d771266 on 20/12/2017.
  */
class BalancingPoolActor extends Actor{
  override def receive: Receive = {
    case msg: String => println(s"I am ${self.path.name}")
    case _ => println(s"I dont understand this message")
  }
}

object BalancingpoolApp extends App {
  val actorSystem = ActorSystem("hello")
  val router = actorSystem.actorOf(BalancingPool(5).props(Props[BalancingPoolActor]))

  (1 to 8).map(i => router ! s"hello $i")
}