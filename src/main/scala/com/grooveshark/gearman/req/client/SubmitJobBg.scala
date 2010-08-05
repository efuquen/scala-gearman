package com.grooveshark.gearman.req.client

import com.grooveshark.gearman.protocol.Packet
import com.grooveshark.gearman.Message

import com.grooveshark.util.NumberUtil._

class SubmitJobBg(
  val function: String,
  val uniqid: Int,
  val workload: String) extends Message{

  def this(packet: Packet) = this(new String(packet.data(0)), bytes2Int(packet.data(1),BIG_ENDIAN,0), new String(packet.data(2)))

  override def toPacket: Packet = {
    null 
  }
}
