name := "processtree-scala"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "com.storm-enroute" %% "scalameter" % "0.7" % Test
)

coverageExcludedPackages := """.*\.common\.Main"""
