package com.packt.chapter6

import akka.persistence.fsm.PersistentFSM.FSMState

/**
  * Created by d771266 on 8/01/2018.
  */

sealed trait CountDownLatchState extends FSMState

case object Closed extends CountDownLatchState { val identifier = "Closed" }
case object Open extends CountDownLatchState { val identifier = "Open" }

case class Count(n: Int = -1)

sealed trait Command
case class Initialize(count: Int) extends Command
case object Mark extends Command

sealed trait DomainEvent
case object LatchDownOpen extends DomainEvent
case class LatchDownClosed(remaining: Int) extends DomainEvent


