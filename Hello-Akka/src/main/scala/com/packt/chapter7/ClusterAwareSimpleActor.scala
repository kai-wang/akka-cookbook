package com.packt.chapter7

import akka.actor.Actor
import akka.cluster.Cluster

/**
  * Created by d771266 on 12/01/2018.
  */
class ClusterAwareSimpleActor extends Actor{

  val cluster = Cluster(context.system)

  override def receive: Receive = {
    case _ => println(s"I have been created at ${cluster.selfUniqueAddress}")
  }
}


