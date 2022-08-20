name := "processtree-scala"

version := "1.0"

scalaVersion := "3.1.3"

scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Yexplicit-nulls", "-language:strictEquality")

libraryDependencies ++= Seq(
  "org.scalatest"     %% "scalatest"  % "3.2.13"   % Test,
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4" % Test
)

Test / parallelExecution := false

logBuffered := false

coverageExcludedPackages := """.*\.common\.Main"""

enablePlugins(JavaAppPackaging)

scalacOptions ++= Seq("-rewrite", "-new-syntax")
