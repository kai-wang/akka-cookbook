package com.packt.chapter4

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by d771266 on 8/01/2018.
  */
object Callback extends App {
  val future = Future(2+2).mapTo[Int]

  future onComplete {
    case Success(result) => println(s"result is $result")
    case Failure(fail) => {
      println("failed")
      fail.printStackTrace()
    }
  }

  println("Executed before callback")

  Await.result(future, 10 seconds)
}
