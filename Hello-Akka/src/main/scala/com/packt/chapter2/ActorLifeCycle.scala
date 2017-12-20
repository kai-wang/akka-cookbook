package com.packt.chapter2

import akka.actor._
import akka.actor.SupervisorStrategy._
import akka.util.Timeout
import scala.concurrent.Await
import scala.concurrent.duration._
import akka.pattern.ask

/**
  * Created by d771266 on 19/12/2017.
  */

case object Error
case class StopActor(actorRef: ActorRef)

class LifeCycleActor extends Actor {
  var sum = 1

  override def preRestart(reason: Throwable, message: Option[Any]): Unit = {
    println(s"sum in preRestart is $sum")
  }

  override def preStart(): Unit = {
    println(s"sum in preStart is $sum")
  }

  override def receive: Receive = {
    case Error => throw new ArithmeticException()
    case _ => println("default msg")
  }

  override def postStop(): Unit = {
    println(s"sum in postStop is ${sum * 3}")
  }

  override def postRestart(reason: Throwable): Unit = {
    sum = sum * 2
    println(s"sum in postRetart is $sum")
  }

}

class Supervisor extends Actor {
  override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case _: ArithmeticException => Restart
    case t => super.supervisorStrategy.decider.applyOrElse(t, (_: Any) => Escalate)
  }

  override def receive: Receive = {
    case (props: Props, name: String) => {
      sender ! context.actorOf(props, name)
    }
    case StopActor(actorRef) => {
      context.stop(actorRef)
    }
  }
}

object ActorLifeCycle extends App{
  implicit val timeout = Timeout(2 seconds)
  val actorSystem = ActorSystem("supervision")
  val supervisor = actorSystem.actorOf(Props[Supervisor], "supervisor")

  val childFuture = supervisor ? (Props(new LifeCycleActor), "lifecycleActor")
  val child = Await.result(childFuture.mapTo[ActorRef], 2 seconds)

  println("call child error")
  child ! Error
  Thread.sleep(1000)
  supervisor ! StopActor(child)
}
