import sbt._

class GearmanProject(info: ProjectInfo) extends DefaultProject(info) {
  //lazy val hi = task { println("Hello World"); None }
  override def compileOptions = super.compileOptions ++ Seq(Deprecation,Verbose,Optimize,Unchecked)

  val slf4japi = "org.slf4j" % "slf4j-api" % "1.5.11"
  val slf4jlog4j = "org.slf4j" %"slf4j-log4j12" % "1.5.11"
}
