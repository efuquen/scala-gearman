package com.grooveshark.gearman.res.client

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.Message

import com.grooveshark.util.NumberUtil._

class WorkComplete(
  val handle: Array[Byte],
  val response: Array[Byte]) extends Message {

  def handleToStr = new String(handle)
  def responseToStr = new String(response)

  def this(handleArg: String, responseArg: String) = this( handleArg.getBytes, responseArg.getBytes )
  def this(packet: Packet) = this(packet.data(0), packet.data(1))

  override def toPacket: Packet = {
    val size = handle.size + response.size + 1
    val header = new PacketHeader(PacketMagic.Res,PacketType.WorkComplete,size)
    new Packet(header,List(handle,response))
  }
}
