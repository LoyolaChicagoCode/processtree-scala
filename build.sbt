name := "processtree-scala"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4" % Test,
  "org.scala-lang.modules" %% "scala-xml" % "1.0.4" % Test,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "com.storm-enroute" %% "scalameter" % "0.7" % Test
)

parallelExecution in Test := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""
