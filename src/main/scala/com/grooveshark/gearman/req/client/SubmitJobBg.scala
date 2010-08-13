package com.grooveshark.gearman.req.client

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.protocol.PacketType._
import com.grooveshark.gearman.Message

import com.grooveshark.util.NumberUtil._

import scala.runtime.RichInt

class SubmitJobBg(
  function: String,
  uniqid: RichInt,
  workload: Array[Byte]) extends Job(PacketType.SubmitJobBg,function,uniqid,workload) {

  def this(function: String,uniqid: RichInt,workload: String) = this(function,uniqid,workload.getBytes)
}
