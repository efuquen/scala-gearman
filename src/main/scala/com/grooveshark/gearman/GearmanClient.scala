package com.grooveshark.gearman

import com.grooveshark.gearman.net.Connection

import com.grooveshark.gearman.res.client._
import com.grooveshark.gearman.req.client._

import com.grooveshark.util.Logger

import scala.runtime.RichInt

class GearmanClient(
  host: String,
  port: Int
) extends Logger{

  def submitJob(function: String, id: RichInt, payload: String): String = {
    val conn = new Connection(host,port)
    try {
      val submitJob = new SubmitJob(function,id,payload)
      conn.send(submitJob.toPacket)
      val jobCreated = new JobCreated(conn.receive)
      debug("JobCreated = %s", jobCreated.handle)
      val workComplete = new WorkComplete(conn.receive)
      debug("WorkComplete = %s", workComplete.handle)
      workComplete.response
    } finally {
      conn.close
    }
  }
}
