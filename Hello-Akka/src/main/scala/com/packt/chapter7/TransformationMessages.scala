package com.packt.chapter7

/**
  * Created by d771266 on 12/01/2018.
  */
final case class TransformationJob(text: String)
final case class TransformationResult(text: String)
final case class JobFailed(reason: String, job: TransformationJob)
case object BackendRegistration