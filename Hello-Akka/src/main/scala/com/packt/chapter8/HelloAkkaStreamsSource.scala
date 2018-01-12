package com.packt.chapter8
import akka.stream.{Attributes, Outlet, SourceShape}
import akka.stream.stage._

/**
  * Created by d771266 on 12/01/2018.
  */
class HelloAkkaStreamsSource extends GraphStage[SourceShape[String]]{
  val out: Outlet[String] = Outlet("SystemInputSource")

  override val shape: SourceShape[String] = SourceShape(out)
  override def createLogic(inheritedAttributes: Attributes): GraphStageLogic =
    new GraphStageLogic(shape) {
      setHandler(out, new OutHandler {
        override def onPull(): Unit = {
          val line = "hello world Akka streams!"
          push(out, line)
        }
      })
    }

}
