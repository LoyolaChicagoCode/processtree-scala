package edu.luc.etl.osdi.processtree.scala
package common

import org.scalameter.api._

/**
  * Common microbenchmark superclass for `buildTree`.
  * This cannot be a trait because it takes a constructor argument.
  */
abstract class Benchmark(label: String) extends Bench.LocalTime with TreeBuilder {

  val sizes: Gen[Int] = Gen.exponential("processes")(100, 1000000, 10)

  val inputs: Gen[Iterator[Process]] = sizes map fakeps.fakePs cached

  performance of label in {
    measure method "buildTree" in {
      using (inputs) in { ps =>
        buildTree(ps)
      }
    }
  }
}
