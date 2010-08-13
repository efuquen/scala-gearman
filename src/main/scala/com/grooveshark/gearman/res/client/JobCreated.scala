package com.grooveshark.gearman.res.client

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.Message

import com.grooveshark.util.NumberUtil._

class JobCreated(
  val handle: Array[Byte]) extends Message {

  def this(handleArg: String) = this(handleArg.getBytes)
  def this(packet: Packet) = this(packet.data(0))

  def handleToStr = new String(handle)

  override def toPacket: Packet = {
    val size = handle.size
    val header = new PacketHeader(PacketMagic.Res,PacketType.JobCreated,size)
    new Packet(header,List(handle))
  }
}
