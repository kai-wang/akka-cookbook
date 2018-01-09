package com.packt.chapter7
import akka.actor.Actor
/**
  * Created by d771266 on 8/01/2018.
  */
class SimpleActor extends Actor {
  def receive = {
    case _ => {
      println(s"Actor is created at ${self.path.address.hostPort}")
    }
  }
}