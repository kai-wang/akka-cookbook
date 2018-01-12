package com.packt.chapter7

/**
  * Created by d771266 on 12/01/2018.
  */
object TransformationApp {

  def main(args: Array[String]): Unit = {
    // starting 2 frontend nodes and 3 backend nodes
    TransformationBackend.main(Seq("2552").toArray)
   // TransformationBackend.main(Array.empty)
   // TransformationBackend.main(Array.empty)

    TransformationFrontend.main(Seq("2551").toArray)
    //TransformationFrontend.main(Array.empty)
  }

}