package com.grooveshark.gearman

class GearmanServer(
  val host: String,
  val port: Int,
  val weight: Double){
  require(weight >= 0.0 && weight <= 1.0)
  require(port > 0 && port < 65537)
  require(host != null)
}
