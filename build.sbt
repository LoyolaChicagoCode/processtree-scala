name := "processtree-scala"

version := "1.1"

scalaVersion := "3.3.0"

scalacOptions += "@.scalacOptions.txt"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
  "org.scalatest"          %% "scalatest"                  % "3.2.16" % Test
)

Test / parallelExecution := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""

enablePlugins(JavaAppPackaging)
