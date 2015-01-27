name := "processtree-scala"

scalaVersion := "2.11.5"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.2" % Test

// The next few lines will work only with sbt-scoverage version 0.99.7.1.
// Do not update until sbt-scoverage 1.0 stabilizes!

instrumentSettings

org.scoverage.coveralls.CoverallsPlugin.coverallsSettings
