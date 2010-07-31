package com.grooveshark.gearman.protocol

object Packet {
  def fromBytes( header: PacketHeader, data: Array[Byte] ): Packet = {
    if( header.size > 0 ) {
      null
    } else {
      new Packet(header,List[String]())
    }
  }
}

class Packet(
  val header: PacketHeader,
  val data: Seq[String]
)
