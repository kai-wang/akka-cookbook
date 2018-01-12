package com.packt.chapter8

import akka.actor.Actor

/**
  * Created by d771266 on 12/01/2018.
  */
class StringCleanerActor extends Actor {
  override def receive: Receive = {
    case s: String => {
      println(s"Cleaning [$s] in StringCleaner")
      sender ! s.replaceAll("""[p{Punct}&&[^.]]""", "").replaceAll(System.lineSeparator(), "")
    }
  }
}
