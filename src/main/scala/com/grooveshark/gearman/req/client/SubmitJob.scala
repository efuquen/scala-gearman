package com.grooveshark.gearman.req.client

import com.grooveshark.gearman.protocol.PacketType

import scala.runtime.RichInt

class SubmitJob(
  function: String,
  uniqid: RichInt,
  workload: Array[Byte]) extends Job(PacketType.SubmitJob,function,uniqid,workload) {

  def this(function: String,uniqid: RichInt,workload: String) = this(function,uniqid,workload.getBytes)
}
