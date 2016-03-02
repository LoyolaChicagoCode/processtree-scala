package edu.luc.etl.osdi.processtree.scala
package common

import org.scalameter.api._

/**
  * Common microbenchmark superclass for `buildTree`.
  * This cannot be a trait because it takes a constructor argument.
  */
abstract class Benchmark(label: String) extends Bench.LocalTime with TreeBuilder {

  val sizes: Gen[Int] = Gen.exponential("processes")(10, 100000, 10)

  val inputs: Gen[Iterator[Process]] = sizes map fakeps.fakePs cached

  performance of label in {
    measure method "buildTree" in {
      using (inputs) in { ps =>
        (1 to 10000) foreach { _ => buildTree(ps) }
      }
    }
  }
}
