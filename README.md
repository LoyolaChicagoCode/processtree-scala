[![Build Status](https://travis-ci.org/LoyolaChicagoCode/processtree-scala.svg?branch=master)](https://travis-ci.org/LoyolaChicagoCode/processtree-scala)
[![Coverage Status](https://coveralls.io/repos/LoyolaChicagoCode/processtree-scala/badge.svg?branch=master)](https://coveralls.io/r/LoyolaChicagoCode/processtree-scala?branch=master)

#Learning Objectives

- exploration of standard problem in Scala
  - OO/imperative, with mutable data structures
  - functional, with immutable data structures

#Usage

To run the tests:

    $ sbt test

To run the main methods on a Unix-based system:

    $ ps -ef | sbt 'runMain edu.luc.etl.osdi.processtree.scala.mutable.Main'
    $ ps -ef | sbt 'runMain edu.luc.etl.osdi.processtree.scala.immutable.Main'

On Windows, if you installed [Git](http://git-scm.com/) with the third option,
*Use Git and optional Unix tools from the Windows Command Prompt*,
then you will have a `ps` command and can run the main methods as in Unix-based
systems but using double-quotes:

    $ ps -ef | sbt "runMain edu.luc.etl.osdi.processtree.scala.mutable.Main"
    $ ps -ef | sbt "runMain edu.luc.etl.osdi.processtree.scala.immutable.Main"
