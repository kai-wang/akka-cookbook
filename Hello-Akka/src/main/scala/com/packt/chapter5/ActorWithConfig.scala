package com.packt.chapter5

import akka.actor.{Actor, ActorSystem, Props}
import com.typesafe.config.{Config, ConfigFactory}

/**
  * Created by d771266 on 8/01/2018.
  */

class MyActor extends Actor {
  override def receive: Receive = {
    case msg: String => println(msg)
  }
}
object ActorWithConfig extends App{
  val config: Config = ConfigFactory.load("akka.conf")
  val system = ActorSystem(config.getString("myactor.actorsystem"))
  val actor = system.actorOf(Props[MyActor], config.getString("myactor.actorname"))

  println(actor.path)
}
