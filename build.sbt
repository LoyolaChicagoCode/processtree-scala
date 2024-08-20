name := "processtree-scala"

version := "1.1"

scalaVersion := "3.3.3"

scalacOptions += "@.scalacOptions.txt"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
  "org.scalatest"          %% "scalatest"                  % "3.2.19" % Test
)

Test / parallelExecution := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""

enablePlugins(JavaAppPackaging)
