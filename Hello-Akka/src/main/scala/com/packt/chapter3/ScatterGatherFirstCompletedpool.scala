package com.packt.chapter3

import akka.actor.{ActorSystem, Actor, Props}
import akka.pattern.ask
import akka.routing.ScatterGatherFirstCompletedPool
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._


/**
  * Created by d771266 on 20/12/2017.
  */

class ScatterGatherFirstCompletedpoolActor extends Actor {
  override def receive: Receive = {
    case msg: String => sender ! "I say hello back to you"
    case _ => println(s"dunno this message")
  }
}

object ScatterGatherFirstCompletedpool extends App{
  implicit val timeout = Timeout(10 seconds)
  val actorSystem = ActorSystem("hello")
  val router = actorSystem.actorOf(ScatterGatherFirstCompletedPool(5, within = 10.seconds).props(Props[ScatterGatherFirstCompletedpoolActor]))

  println(Await.result((router ? "hello").mapTo[String], 10 seconds))

}
