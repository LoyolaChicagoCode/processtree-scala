name := "processtree-scala"

version := "0.2"

scalaVersion := "2.12.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"  % "3.0.5" % Test,
  "com.storm-enroute" %% "scalameter" % "0.10.1" % Test,
  "org.scala-stm"     %% "scala-stm"  % "0.8"   % Test
)

parallelExecution in Test := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""
