package com.grooveshark.gearman.protocol

import scala.collection.mutable.ListBuffer

object Packet {
  def fromBytes(data: Array[Byte]): Packet = {
    require(data != null); require(data.size >= 12)
    val header = PacketHeader.fromBytes(data)
    if( header.size > 0 ) {
      var args = new ListBuffer[Byte] 
      var payload = List[Array[Byte]]() 
      for( datum <- data ) {
        if( datum != '\0' ) {
          args += datum
        } else {
          payload = args.toArray :: payload
          args = new ListBuffer[Byte]
        }
      }
      new Packet(header,payload.reverse)
    } else {
      new Packet(header,List[Array[Byte]]())
    }
  }
}

class Packet(
  val header: PacketHeader,
  val data: Seq[Array[Byte]]
) {
  def toBytes: Array[Byte] = {
    val packetBytes = new ListBuffer[Byte]
    val headerBytes = header.toBytes
    headerBytes.foreach(packetBytes += _)
    var i = 0
    data.foreach(datum => { 
      datum.foreach(datumByte => packetBytes += datumByte)
      if(i < data.size - 1)
        packetBytes += '\0'
      i += 1
    })
    packetBytes.toArray
  }
}
