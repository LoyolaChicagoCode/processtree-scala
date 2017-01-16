name := "processtree-scala"

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "com.storm-enroute" %% "scalameter" % "0.8.2" % Test,
  "org.scala-stm" %% "scala-stm" % "0.8" % Test
)

parallelExecution in Test := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""
