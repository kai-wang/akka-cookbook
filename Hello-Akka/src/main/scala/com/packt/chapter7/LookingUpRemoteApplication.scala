package com.packt.chapter7
import akka.actor.{ActorRef, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.duration._
import scala.util.{Failure, Success}


/**
  * Created by d771266 on 9/01/2018.
  */
object LookingUpActorSelection extends App{
  val config: Config = ConfigFactory.load("application-1.conf")
  val actorsystem = ActorSystem("LookupingUpActors", config)
  implicit val dispatcher = actorsystem.dispatcher

  val selection = actorsystem.actorSelection("akka.tcp//LookingUpRemoteActors@127.0.0.1:2553/user/remoteActor")
  selection ! "test"

  selection.resolveOne(5 seconds).onSuccess {
    case actorRef: ActorRef => {
      println(s"we got an actor ${actorRef.path}")
      actorRef ! "testtesttest"
    }
  }
}

object LookingUpRemoteActors extends App {
  val config: Config = ConfigFactory.load("application-3.conf")
  val actorSystem = ActorSystem("LookingUpRemoteActors", config)
  actorSystem.actorOf(Props[SimpleActor], "remoteActor")
}
