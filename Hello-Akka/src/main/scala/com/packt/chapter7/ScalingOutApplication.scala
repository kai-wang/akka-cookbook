package com.packt.chapter7
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.routing.RoundRobinPool
import scala.concurrent.duration._

/**
  * Created by d771266 on 10/01/2018.
  */
object ScalingOutWorker extends App {
  val actorSystem = ActorSystem("WorkerActorSystem")
  implicit val dispatcher = actorSystem.dispatcher
  val selection = actorSystem.actorSelection("akka.tcp://MasterActorSystem@127.0.0.1:2552/user/masterActor")

  selection.resolveOne(3 seconds).onSuccess {
      case masterActor : ActorRef =>
      println("We got the ActorRef for the master actor")
      val pool = RoundRobinPool(10)
      val workerPool = actorSystem.actorOf(Props[WorkerActor].withRouter(pool), "workerActor")
      masterActor ! RegisterWorker(workerPool)
  }
}


object ScalingOutMaster extends App {
  val actorSystem = ActorSystem("MasterActorSystem")
  val masterActor = actorSystem.actorOf(Props[MasterActor], "masterActor")
  (1 to 100).foreach(i => {
    println(s"sending new work $i")
    masterActor ! Work(s"$i")
    Thread.sleep(5000) //Simulates sending work to the master actor every 5 seconds
  })
}