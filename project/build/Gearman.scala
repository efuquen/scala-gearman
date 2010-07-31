import sbt._

class GearmanProject(info: ProjectInfo) extends DefaultProject(info) {
  //lazy val hi = task { println("Hello World"); None }
  override def compileOptions = super.compileOptions ++ Seq(Deprecation,Verbose,Optimize,Unchecked)
}
