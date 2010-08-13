package com.grooveshark.gearman.req.worker

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.protocol.PacketType._
import com.grooveshark.gearman.Message

class WorkFail(
  handle: String
) extends Message {

  override def toPacket: Packet = {
    val handleBytes = handle.getBytes

    val size = handleBytes.size
    val header = new PacketHeader(PacketMagic.Req,PacketType.WorkFail,size)
    new Packet(header,List(handleBytes))
  }
}
