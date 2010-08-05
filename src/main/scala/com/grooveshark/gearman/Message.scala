package com.grooveshark.gearman

import com.grooveshark.gearman.protocol.Packet

abstract class Message {
  def toPacket: Packet
}
