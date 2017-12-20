package com.packt.chapter1
import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
/**
  * Created by d771266 on 18/12/2017.
  */

class FibonacciActor extends Actor {
  override def receive: Receive = {
    case num: Int => {
      val fibonacciNumber = fib(num)
      sender ! fibonacciNumber
    }
  }

  def fib(n: Int): Int = n match {
    case 0 | 1 => n
    case _ => fib(n-1) + fib(n-2)
  }
}
object FibonacciActor extends App{
  implicit val timeout = Timeout(10 seconds)
  val actorSystem = ActorSystem("HelloAkka")
  val actor = actorSystem.actorOf(Props[FibonacciActor])
  val future = (actor ? 10).mapTo[Int]
  val fiboacciNumber = Await.result(future, 10 seconds)
  println(fiboacciNumber)
}
