package com.grooveshark.gearman.protocol

object PacketType extends Enumeration {

  type PacketType = Value

  val CanDo = new Val(1, "CanDo_Req_Worker")
  val CantDo = new Val(2, "CantDo_Req_Worker")
  val ResetAbilities = new Val(3, "ResetAbilities_Req_Worker")
  val PreSleep = new Val(4, "PreSleep_Req_Worker")
  val NoOp = new Val(6, "")
  val SubmitJob = new Val(7, "")
  val JobCreated = new Val(8, "")
  val GrabJob = new Val(9, "")
  val NoJob = new Val(10, "")
  val JobAssign = new Val(11, "")
  val WorkStatus = new Val(12, "")
  val WorkComplete = new Val(13, "")
  val WorkFail = new Val(14, "")
  val GetStatus = new Val(15, "")
  val EchoReq = new Val(16, "")
  val EchoRes = new Val(17, "")
  val SubmitJobBg = new Val(18, "")
  val Error = new Val(19, "")
  val StatusRes = new Val(20, "")
  val SubmitJobHigh = new Val(21, "")
  val SetClientID = new Val(22, "")
  val CanDoTimeout = new Val(23, "")
  val AllYours = new Val(24, "")
  val WorkException = new Val(25, "")
  val OptionReq = new Val(26, "")
  val OptionRes = new Val(27, "")
  val WorkData = new Val(28, "")
  val WorkWarning = new Val(29, "")
  val GrabJobUniq = new Val(30, "")
  val JobAssignUniq = new Val(31, "")
  val SubmitJobHighBg = new Val(32, "")
  val SubmitJobLow = new Val(33, "")
  val SubmitJobLowBg = new Val(34, "")
  val SubmitJobSched = new Val(35, "")
  val SubmitJobEpoch = new Val(36, "")
}

object PacketMagic extends Enumeration {
  type PacketMagic = Value

  val Req = new Val("\0REQ")
  val Res = new Val("\0RES")
}

object PacketHeader {
  def fromBytes( data: Array[Byte] ): PacketHeader = {
    require(data != null); require(data.size >= 16)

    val pMagic = PacketMagic.withName( new String( Array( data(0), data(1), data(2), data(3) ) ) )
    val pType = PacketType(((data(4) << 24) & 0xFF000000) + ((data(5) << 16) & 0xFF000000) + ((data(6) << 8) & 0x0000FF00) + (data(7) & 0x000000FF))
    val size = (((data(8) << 24) & 0xFF000000) + ((data(9) << 16) & 0xFF000000) + ((data(10) << 8) & 0x0000FF00) + (data(11) & 0x000000FF)).toLong

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
}
