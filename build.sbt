name := "processtree-scala"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % Test

coverageExcludedPackages := """.*\.common\.Main"""
