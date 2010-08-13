package com.grooveshark.gearman

class GearmanResult(
  val result: Array[Byte],
  val handle: Array[Byte])
{
  def resultToStr = new String(result)
  def handleToStr = new String(handle)
}
