[![Build Status](https://travis-ci.org/LoyolaChicagoCode/processtree-scala.svg?branch=master)](https://travis-ci.org/LoyolaChicagoCode/processtree-scala)
[![Coverage Status](https://coveralls.io/repos/LoyolaChicagoCode/processtree-scala/badge.svg?branch=master)](https://coveralls.io/r/LoyolaChicagoCode/processtree-scala?branch=master)

# Learning Objectives

- exploration of a standard problem in Scala:
  edge reversal algorithm:
  converting a representation of a tree as a flat sequence of child-to-parent
  edges to one where each parent maps to a sequence of children
  - OO/imperative, with mutable data structures
  - functional, with immutable data structures
- software design principles
  - [DRY](http://en.wikipedia.org/wiki/Don%27t_repeat_yourself)
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

To run the benchmarks:

    $ sbt 'test:runMain edu.luc.etl.osdi.processtree.scala.mutable.Benchmark -silent'
    $ sbt 'test:runMain edu.luc.etl.osdi.processtree.scala.groupby.Benchmark -silent'
    $ sbt 'test:runMain edu.luc.etl.osdi.processtree.scala.fold.Benchmark -silent'

On Windows, if you installed [Git](http://git-scm.com/) with the recommended
third option,
*Use Git and optional Unix tools from the Windows Command Prompt*,
then you will have a `ps` command available.