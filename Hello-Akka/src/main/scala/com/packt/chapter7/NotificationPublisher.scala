package com.packt.chapter7
import akka.actor.Actor
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish

/**
  * Created by d771266 on 10/01/2018.
  */
class NotificationPublisher extends Actor{
  val mediator = DistributedPubSub(context.system).mediator

  override def receive: Receive = {
    case notification: Notification => mediator ! Publish("notification", notification)
  }

}
