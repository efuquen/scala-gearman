package com.grooveshark.gearman.protocol

import com.grooveshark.util.NumberUtil._

object PacketType extends Enumeration {

  type PacketType = Value

  val CanDo = new Val(1, "CanDo")
  val CantDo = new Val(2, "CantDo")
  val ResetAbilities = new Val(3, "ResetAbilities")
  val PreSleep = new Val(4, "PreSleep")
  val NoOp = new Val(6, "NoOp")
  val SubmitJob = new Val(7, "SubmitJob")
  val JobCreated = new Val(8, "JobCreated")
  val GrabJob = new Val(9, "GrabJob")
  val NoJob = new Val(10, "NoJob")
  val JobAssign = new Val(11, "JobAssign")
  val WorkStatus = new Val(12, "WorkStatus")
  val WorkComplete = new Val(13, "WorkComplete")
  val WorkFail = new Val(14, "WorkFail")
  val GetStatus = new Val(15, "GetStatus")
  val EchoReq = new Val(16, "EchoReq")
  val EchoRes = new Val(17, "EchoRes")
  val SubmitJobBg = new Val(18, "SubmitJobBg")
  val Error = new Val(19, "Error")
  val StatusRes = new Val(20, "StatusRes")
  val SubmitJobHigh = new Val(21, "SubmitJobHigh")
  val SetClientID = new Val(22, "SetClientID")
  val CanDoTimeout = new Val(23, "CanDoTimeout")
  val AllYours = new Val(24, "AllYours")
  val WorkException = new Val(25, "WorkException")
  val OptionReq = new Val(26, "OptionReq")
  val OptionRes = new Val(27, "OptionRes")
  val WorkData = new Val(28, "WorkData")
  val WorkWarning = new Val(29, "WorkWarning")
  val GrabJobUniq = new Val(30, "GrabJobUniq")
  val JobAssignUniq = new Val(31, "JobAssignUniq")
  val SubmitJobHighBg = new Val(32, "SubmitJobHighBg")
  val SubmitJobLow = new Val(33, "SubmitJobLow")
  val SubmitJobLowBg = new Val(34, "SubmitJobLowBg")
  val SubmitJobSched = new Val(35, "SubmitJobSched")
  val SubmitJobEpoch = new Val(36, "SubmitJobEpoch")
}

object PacketMagic extends Enumeration {
  type PacketMagic = Value

  val Req = new Val("\0REQ")
  val Res = new Val("\0RES")
}

object PacketHeader {
  def fromBytes( data: Array[Byte] ): PacketHeader = {
    require(data != null); require(data.size >= 12)

    val pMagic = PacketMagic.withName( new String( Array( data(0), data(1), data(2), data(3) ) ) )
    val pType = PacketType(bytes2Int(data,BIG_ENDIAN,4))
    val size = bytes2Int(data,BIG_ENDIAN,8).toLong

    new PacketHeader(pMagic,pType,size)
  }
}

import PacketMagic._
import PacketType._
class PacketHeader(
  val pMagic: PacketMagic,
  val pType: PacketType,
  val size: Long
) {
  require(size > 0)

  def toBytes: Array[Byte] = {
    val headerBytes = new Array[Byte](12)

    moveBytes(pMagic.toString.getBytes,0,headerBytes,0)
    int2Bytes(headerBytes,pType.id,BIG_ENDIAN,4)
    int2Bytes(headerBytes,size,BIG_ENDIAN,8)

    return headerBytes
  }

  override def toString: String = {
    pMagic.toString + " " + pType + " " + size
  }
}
