package com.packt.chapter4

import akka.actor.{Actor, ActorSystem, Props}
import com.packt.chapter4.ForComprehensions.finalFuture

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._


/**
  * Created by d771266 on 8/01/2018.
  */
class FutureActor extends Actor {
  import context.dispatcher

  def receive = {
    case (a: Int, b: Int) => {
      val f = Future(a + b)
      println("result :" + Await.result(f, 10 seconds))
    }
  }
}

object FutureInsideActor extends App {
  val actorSystem = ActorSystem("Hello-Akka")
  val fActor = actorSystem.actorOf(Props[FutureActor])

 fActor !(20, 30)

}
