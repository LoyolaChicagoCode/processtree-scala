name := "processtree-scala"

version := "0.2"

scalaVersion := "2.13.3"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"  % "3.2.2"   % Test,
  "com.storm-enroute" %% "scalameter" % "0.19"    % Test,
  "org.scala-stm"     %% "scala-stm"  % "0.9.1"   % Test,
  "org.scala-lang.modules" %% "scala-parallel-collections" % "0.2.0" % Test
)

parallelExecution in Test := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""
