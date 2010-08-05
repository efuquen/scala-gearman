package com.grooveshark.gearman.res.client

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.Message

import com.grooveshark.util.NumberUtil._

class JobCreated(
  val handle: String) extends Message {

  def this(packet: Packet) = this(new String(packet.data(0)))

  override def toPacket: Packet = {
    val handleBytes = handle.getBytes
    val size = handleBytes.size

    val header = new PacketHeader(PacketMagic.Res,PacketType.JobCreated,size)

    new Packet(header,List(handleBytes))
  }
}
