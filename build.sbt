name := "processtree-scala"

version := "0.2"

scalaVersion := "2.13.6"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"  % "3.2.9"   % Test,
  "com.storm-enroute" %% "scalameter" % "0.21"    % Test,
  "org.scala-stm"     %% "scala-stm"  % "0.11.1"  % Test,
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.3" % Test
)

parallelExecution in Test := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""

enablePlugins(JavaAppPackaging)
