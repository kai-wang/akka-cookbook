package com.packt.chapter2
import akka.actor.SupervisorStrategy.{Escalate, Stop, Resume, Restart}
import akka.actor._
import scala.concurrent.duration._

/**
  * Created by d771266 on 20/12/2017.
  */

case class Add(a: Int, b: Int)
case class Sub(a: Int, b: Int)
case class Div(a: Int, b: Int)

class Caculator(printer: ActorRef) extends Actor {
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("Calculator: I am restarting because of ArithemticException")
  }

  override def receive: Receive = {
    case Add(a, b) => printer ! s"sum is ${a + b}"
    case Sub(a, b) => printer ! s"diff is ${a - b}"
    case Div(a, b) => printer ! s"div is ${a / b}"
  }
}

class ResultPrinter extends Actor {
  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println("Printer: I am restarting as wwell")
  }

  override def receive: Receive = {
    case msg => println(msg)
  }
}
class AllForOneStrategySupervisor extends Actor {
  override val supervisorStrategy = AllForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 seconds) {
    case _: ArithmeticException => Restart
    case _: NullPointerException => Resume
    case _: IllegalArgumentException => Stop
    case _: Exception => Escalate
  }

  val printer = context.actorOf(Props[ResultPrinter])
  val calculator = context.actorOf(Props(classOf[Caculator], printer))

  override def receive: Receive = {
    case "Start" => {
      calculator ! Add(10, 22)
      calculator ! Sub(12, 2)
      calculator ! Div(5, 2)
      calculator ! Div(5, 0)
    }
  }
}

object AllForOneStrategyApp extends App{
  val system = ActorSystem("hello")
  val supervisor = system.actorOf(Props[AllForOneStrategySupervisor], "supervisor")

  supervisor ! "Start"
}
