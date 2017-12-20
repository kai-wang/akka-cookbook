package com.packt.chapter1
import akka.actor.{Actor, Props, ActorSystem}

/**
  * Created by d771266 on 18/12/2017.
  */
class SummingActorWithConstructor(initalSum: Int) extends Actor {
  var sum = 0

  override def receive: Receive = {
    case x: Int => {
      sum = initalSum + sum + x
      println(s"my state as num is $sum")
    }
    case _ => println("dunno")
  }
}

class SummingActor extends Actor {
  var sum = 0

  override def receive: Receive = {
    case x: Int => {
      sum = sum + x
      println(s"my state as num is $sum")
    }
    case _ => println("dunno")
  }
}
object BehaviorAndState extends App {
  val actorSystem = ActorSystem("HelloAkka")
  //val actor = actorSystem.actorOf(Props[SummingActor], "summingactor")
  val actor = actorSystem.actorOf(Props(classOf[SummingActorWithConstructor], 10), "summingActor")
  println(actor.path)

  var count = 0
  while (true && count < 5) {
    Thread.sleep(2000)
    count += 1
    actor ! 1
  }
}
