[![Build Status](https://travis-ci.org/LoyolaChicagoCode/processtree-scala.svg?branch=master)](https://travis-ci.org/LoyolaChicagoCode/processtree-scala)
[![codecov](https://codecov.io/gh/LoyolaChicagoCode/processtree-scala/branch/master/graph/badge.svg)](https://codecov.io/gh/LoyolaChicagoCode/processtree-scala)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/b3584cb7773b4a1e8e951bde126482e7)](https://www.codacy.com/app/laufer/processtree-scala?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=LoyolaChicagoCode/processtree-scala&amp;utm_campaign=Badge_Grade)

[![Issue Stats](http://issuestats.com/github/LoyolaChicagoCode/processtree-scala/badge/pr)](http://issuestats.com/github/LoyolaChicagoCode/processtree-scala)
[![Issue Stats](http://issuestats.com/github/LoyolaChicagoCode/processtree-scala/badge/issue)](http://issuestats.com/github/LoyolaChicagoCode/processtree-scala)
[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/LoyolaChicagoCode/processtree-scala.svg)](http://isitmaintained.com/project/LoyolaChicagoCode/processtree-scala "Average time to resolve an issue")
[![Percentage of issues still open](http://isitmaintained.com/badge/open/LoyolaChicagoCode/processtree-scala.svg)](http://isitmaintained.com/project/LoyolaChicagoCode/processtree-scala "Percentage of issues still open")

# Learning Objectives

- exploration of a standard problem in Scala:
  edge reversal algorithm:
  converting a representation of a tree as a flat sequence of child-to-parent
  edges to one where each parent maps to a sequence of children
  - OO/imperative, with mutable data structures
  - functional, with immutable data structures
- software design principles
  - [SoC](https://en.wikipedia.org/wiki/Separation_of_concerns) (separation of concerns)
  - [DRY](http://en.wikipedia.org/wiki/Don%27t_repeat_yourself) (don't repeat yourself)
  - [testcase superclass](http://xunitpatterns.com/Testcase%20Superclass.html)
- Scala programming techniques
  - console input
  - [stackable traits](http://www.artima.com/scalazine/articles/stackable_trait_pattern.html)
  - unit testing using [ScalaTest](http://www.scalatest.org)
  - automated performance testing/microbenchmarking using [ScalaMeter](https://scalameter.github.io)

# Overview

This is a Scala-based solution to the
[process tree homework assignment](http://osdi.etl.luc.edu/homework/home-lab-assignment-1)
from the course
[COMP 374/410: Introduction to Operating Systems](http://osdi.etl.luc.edu).

# Usage

To run the tests:

    $ sbt test

To run the main methods:

    $ ps -ef | sbt "runMain edu.luc.etl.osdi.processtree.scala.mutable.Main"
    $ ps -ef | sbt "runMain edu.luc.etl.osdi.processtree.scala.groupby.Main"
    $ ps -ef | sbt "runMain edu.luc.etl.osdi.processtree.scala.fold.Main"

To generate larger data sets for testing:

    $ sbt "test:runMain edu.luc.etl.osdi.processtree.scala.fakeps.Main 100000" > data.txt

To run the benchmarks:

    $ sbt "test:runMain edu.luc.etl.osdi.processtree.scala.fakeps.Benchmark"
    $ sbt "test:runMain edu.luc.etl.osdi.processtree.scala.mutable.Benchmark"
    $ sbt "test:runMain edu.luc.etl.osdi.processtree.scala.groupby.Benchmark"
    $ sbt "test:runMain edu.luc.etl.osdi.processtree.scala.fold.Benchmark"

On Windows, if you installed [Git](http://git-scm.com/) with the recommended
third option, *Use Git and optional Unix tools from the Windows Command Prompt*,
then you will have a `ps` command available.