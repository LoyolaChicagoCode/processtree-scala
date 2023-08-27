name := "processtree-scala"

version := "1.0"

scalaVersion := "3.2.1"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Yexplicit-nulls", "-Ysafe-init", "-language:strictEquality")

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"  % "3.2.16"   % Test,
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4" % Test
)

Test / parallelExecution := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""

enablePlugins(JavaAppPackaging)
