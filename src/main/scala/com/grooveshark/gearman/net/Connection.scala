package com.grooveshark.gearman.net

import com.grooveshark.gearman.GearmanServer

import com.grooveshark.gearman.protocol.{Packet,PacketHeader}

import com.grooveshark.util.IOUtil._
import com.grooveshark.util.Logger

import java.net.Socket

import java.io.{BufferedInputStream,BufferedOutputStream}

class Connection(
  host: String,
  port: Int) extends Logger {

  def this(server: GearmanServer) = this(server.host,server.port)

  val socket = new Socket(host,port)
  val in = new BufferedInputStream(socket.getInputStream)
  val out = new BufferedOutputStream(socket.getOutputStream)

  def send(packet: Packet) {
    out.write(packet.toBytes)
    out.flush
  }

  def receive: Packet = {
    val headerBytes = new Array[Byte](12)
    readFully(in,headerBytes)
    val header = PacketHeader.fromBytes(headerBytes)
    debug("Got Header = %s", header)
    val data = new Array[Byte](header.size.toInt)
    readFully(in,data)
    debug("Got Data = %s", data.foldLeft[String]("")(_ + " " +  _))
    Packet.fromBytes(header,data)
  }

  def close = socket.close
}
