package com.packt.chapter5

import akka.actor.{ActorSystem, Actor, Props}
import akka.pattern.{CircuitBreaker, ask}
import akka.util.Timeout
import scala.concurrent.duration._

/**
  * Created by d771266 on 8/01/2018.
  */

case class FetchRecord(recordID: Int)
case class Person(name: String, age: Int)

object DB {
  val data = Map(1 -> Person("John", 21), 2 -> Person("Peter", 30), 3 -> Person("James", 40), 4 -> Person("Alice", 25))
}

class DBActor extends Actor {
  override def receive: Receive = {
    case FetchRecord(recordID) => {
      if(recordID >= 3 && recordID <=5)
        Thread.sleep(3000)
      else
        sender ! DB.data.get(recordID)
    }
  }
}


object CircuitBreakerApp extends App{
  val system = ActorSystem("hello")

  implicit val ec = system.dispatcher
  implicit val timeout = Timeout(3 seconds)

  val breaker = new CircuitBreaker(system.scheduler,
    maxFailures = 3,
    callTimeout = 1 seconds,
    resetTimeout = 2 seconds).
    onOpen(println("===========State is open=============")).
    onClose(println("==========State is closed============")
    )

  val db = system.actorOf(Props[DBActor], "DBActor")

  (1 to 10).map(recordID => {
    Thread.sleep(3000)
    val askFuture = breaker.withCircuitBreaker(db ? FetchRecord(recordID))
    askFuture.map(record => s"Record is : $record and RecordID $recordID").recover({
      case fail => "Failed with : " + fail.toString
    }).foreach(x => println(x))
  })
}
