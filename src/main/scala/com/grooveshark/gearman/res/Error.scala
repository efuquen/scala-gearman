package com.grooveshark.gearman.res

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.Message

class Error(
  val errCode: String,
  val errMsg: String) extends Message {

  def this(packet: Packet) = this(new String(packet.data(0)), new String(packet.data(1)))

  override def toPacket: Packet = {
    val errCodeBytes = errCode.getBytes
    val errMsgBytes = errMsg.getBytes

    val size = errCodeBytes.size + errMsgBytes.size + 1

    val header = new PacketHeader(PacketMagic.Res,PacketType.Error,size)
    new Packet(header,List(errCodeBytes,errMsgBytes))
  }
}
