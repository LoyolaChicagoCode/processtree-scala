name := "processtree-scala"

version := "0.2"

scalaVersion := "2.12.4"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"  % "3.0.1" % Test,
  "com.storm-enroute" %% "scalameter" % "0.8.2" % Test,
  "org.scala-stm"     %% "scala-stm"  % "0.8"   % Test
)

parallelExecution in Test := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""
