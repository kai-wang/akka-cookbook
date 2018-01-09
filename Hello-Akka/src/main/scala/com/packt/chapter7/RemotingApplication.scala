package com.packt.chapter7
import akka.actor.{ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by d771266 on 8/01/2018.
  */

object HelloAkkaRemoting1 extends App {
  //val config: Config = ConfigFactory.load("application-1.conf")
  val actorSystem = ActorSystem("HelloAkkaRemoting1")
}

object HelloAkkaRemoting2 extends App {
  //val config: Config = ConfigFactory.load("application-2.conf")
  val actorSystem = ActorSystem("HelloAkkaRemoting2")
  println("Creating actor from HelloAkkaRemoting2")
  val actor = actorSystem.actorOf(Props[SimpleActor], "simpleRemoteActor")
  actor ! "Checking"
}