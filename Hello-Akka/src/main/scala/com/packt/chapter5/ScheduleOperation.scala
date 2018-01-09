package com.packt.chapter5

import akka.actor.ActorSystem
import scala.concurrent.duration._

/**
  * Created by d771266 on 8/01/2018.
  */
object ScheduleOperation extends App{
  val system = ActorSystem("hello")
  import system.dispatcher

  system.scheduler.scheduleOnce(10 seconds) {
    println("scheudle once")
  }

  system.scheduler.schedule(11 seconds, 2 seconds) {
    println("schedule every 2 seconds")
  }
}
