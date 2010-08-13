package com.grooveshark.gearman.req.worker

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.protocol.PacketType._
import com.grooveshark.gearman.Message

class WorkWarning(
  handle: String,
  data: String
) extends Message {

  override def toPacket: Packet = {
    val handleBytes = handle.getBytes
    val dataBytes = data.getBytes

    val size = handleBytes.size + dataBytes.size + 1
    val header = new PacketHeader(PacketMagic.Req,PacketType.WorkWarning,size)
    new Packet(header,List(handleBytes,dataBytes))

  }
}
