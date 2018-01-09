package com.packt.chapter4

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by d771266 on 8/01/2018.
  */
object ForComprehensions extends App{
  val futureA = Future(20 + 30)
  val futureB = Future(30 + 30)

  val finalFuture: Future[Int] = for {
    a <- futureA
    b <- futureB
  } yield a + b

  println("Future result is " + Await.result(finalFuture, 1 seconds))

}
