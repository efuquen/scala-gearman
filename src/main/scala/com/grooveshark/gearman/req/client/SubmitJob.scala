package com.grooveshark.gearman.req.client

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic,PacketType}
import com.grooveshark.gearman.Message

import com.grooveshark.util.NumberUtil._

import scala.runtime.RichInt

class SubmitJob(
  val function: String,
  val uniqid: RichInt,
  val workload: String) extends Message{

  def this(packet: Packet) = this(new String(packet.data(0)), bytes2Int(packet.data(1), BIG_ENDIAN,0), new String(packet.data(2)))

  override def toPacket: Packet = {
    val functionBytes = function.getBytes
    val uniqidBytes = if( uniqid != null ) {
      int2Bytes(uniqid.abs,BIG_ENDIAN)
    } else {
      Array[Byte]()
    }
    val workloadBytes = workload.getBytes

    val size = functionBytes.size + uniqidBytes.size + workloadBytes.size + 2
    val header = new PacketHeader(PacketMagic.Req,PacketType.SubmitJob,size)
    new Packet(header,List(functionBytes,uniqidBytes,workloadBytes))
  }
}
