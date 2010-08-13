package com.grooveshark.gearman.req.client

import com.grooveshark.gearman.protocol.{PacketHeader,Packet,PacketMagic}
import com.grooveshark.gearman.protocol.PacketType._
import com.grooveshark.gearman.Message

import com.grooveshark.util.NumberUtil._

import scala.runtime.RichInt

abstract class Job(
  val pType: PacketType,
  val function: String,
  val uniqid: RichInt,
  val workload: Array[Byte]) extends Message{

  def this(packet: Packet) = this(packet.header.pType, new String(packet.data(0)), bytes2Int(packet.data(1), BIG_ENDIAN,0), packet.data(2))
  def this(pType: PacketType, function: String, uniqid: RichInt, workload: String) = this(pType,function,uniqid,workload.getBytes)

  def workloadToStr = new String(workload)

  override def toPacket: Packet = {
    val functionBytes = function.getBytes
    val uniqidBytes = if( uniqid != null ) {
      int2Bytes(uniqid.abs,BIG_ENDIAN)
    } else {
      Array[Byte]()
    }
    val size = functionBytes.size + uniqidBytes.size + workload.size + 2
    val header = new PacketHeader(PacketMagic.Req,pType,size)
    new Packet(header,List(functionBytes,uniqidBytes,workload))
  }
}
