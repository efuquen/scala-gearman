package com.grooveshark.gearman.res

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.Message

class EchoRes(
  val data: Array[Byte]) extends Message {

  def this(packet: Packet) = this(packet.data(0))
  def this(dataArg: String) = this(dataArg.getBytes)

  def dataToStr = new String(data)
  override def toPacket: Packet = {
    val size = data.size
    val header = new PacketHeader(PacketMagic.Res,PacketType.EchoRes,size)
    new Packet(header,List(data))
  }
}
