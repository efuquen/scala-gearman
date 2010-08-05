package com.grooveshark.gearman.res.client

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.Message

import com.grooveshark.util.NumberUtil._

class WorkComplete(
  val handle: String,
  val response: String) extends Message {

  def this(packet: Packet) = this(new String(packet.data(0)), new String(packet.data(1)))

  override def toPacket: Packet = {
    val handleBytes = handle.getBytes
    val responseBytes = response.getBytes

    val size = handleBytes.size + responseBytes.size + 1
    val header = new PacketHeader(PacketMagic.Res,PacketType.WorkComplete,size)
    new Packet(header,List(handleBytes,responseBytes))
  }
}
