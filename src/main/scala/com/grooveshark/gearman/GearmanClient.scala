package com.grooveshark.gearman

import com.grooveshark.gearman.net.Connection

import com.grooveshark.gearman.err.GearmanException

import com.grooveshark.gearman.res.client._
import com.grooveshark.gearman.req.client._

import com.grooveshark.util.Logger
import com.grooveshark.util.NumberUtil._

import scala.runtime.RichInt

class GearmanClient(
  servers: List[GearmanServer]
) extends Logger{

  require(servers.foldLeft[Double](0.0)(_ + _.weight) == 1.0)

  var marker = 0.0
  val eligibleServers = servers.filter(_.weight > 0.0).map[Tuple3[GearmanServer,Double,Double],List[Tuple3[GearmanServer,Double,Double]]]( server => {
    marker += server.weight
    (server, marker - server.weight, marker)
  })

  def this(host: String, port: Int, weight: Double) = this(List(new GearmanServer(host,port,weight)))
  def this(host: String, port: Int) = this(host,port,1.0)
  def this(host: String) = this(host, 4730)

  private def createConn = new Connection(pickServer)

  private def pickServer: GearmanServer = {
    val randNum = rand
    eligibleServers.find(server => randNum > server._2 && randNum < server._3) match {
      case Some(server) => server._1
      case None => throw new GearmanException("Can't find an eligible server. randNum=" + randNum + " servers=" + eligibleServers)
    }
  }
    
  def submitJob(function: String, id: RichInt, payload: String): GearmanResult = {
    val conn = createConn 
    try {
      val submitJob = new SubmitJob(function,id,payload)
      conn.send(submitJob.toPacket)
      val jobCreated = new JobCreated(conn.receive)
      val workComplete = new WorkComplete(conn.receive)
      new GearmanResult(workComplete.response, jobCreated.handle)
    } finally {
      conn.close
    }
  }

  def submitJobBg(function: String, id: RichInt, payload: String): GearmanResult = {
    val conn = createConn 
    try {
      val submitJob = new SubmitJobBg(function,id,payload)
      conn.send(submitJob.toPacket)
      val jobCreated = new JobCreated(conn.receive)
      new GearmanResult(Array[Byte](), jobCreated.handle)
    } finally {
      conn.close
    }
  }
}
