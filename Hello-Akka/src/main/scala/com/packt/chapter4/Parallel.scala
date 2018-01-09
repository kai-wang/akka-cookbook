package com.packt.chapter4

import com.packt.chapter4.Parallel.sum

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

/**
  * Created by d771266 on 8/01/2018.
  */

object Fib {
  def fib(n: Int): Int = {
    def fib_tail(n: Int, a: Int, b: Int): Int = n match {
      case 0 => a
      case _ => fib_tail(n-1, b, a+b)
    }

    fib_tail(n, 0, 1)
  }
}
object Parallel extends App{
  import Fib._

  val t1 = System.currentTimeMillis
  val sum = fib(100) + fib(100) + fib(100)

  println(s"sum is $sum time taken in sequential computation ${(System.currentTimeMillis() - t1) / 1000.0} " )

  val t2 = System.currentTimeMillis

  val future1 = Future(fib(100))
  val future2 = Future(fib(100))
  val future3 = Future(fib(100))

  val future = for {
    f1 <- future1
    f2 <- future2
    f3 <- future3
  } yield (f1+f2+f3)


  future onSuccess {
    case s => {
      println(s"sum is $s time taken in parallel computation ${(System.currentTimeMillis() - t2) / 1000.0} ")
    }
  }

  Thread.sleep(5000)

}
