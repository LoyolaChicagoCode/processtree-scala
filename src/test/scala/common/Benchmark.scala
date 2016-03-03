package edu.luc.etl.osdi.processtree.scala
package common

import org.scalameter.api._

/**
  * Common microbenchmark superclass for `buildTree`.
  * This cannot be a trait because it takes a constructor argument.
  */
abstract class Benchmark(label: String) extends Bench.LocalTime with TreeBuilder {

  val sizes: Gen[Int] = Gen.exponential("processes")(10, 1000000, 10)

  /** Inputs cached as (immutable) sequences so we can reuse them. */
  val inputs: Gen[Seq[Process]] = sizes.map(n => fakeps.fakePs(n).toSeq).cached

  performance of label in {
    measure method "buildTree" in {
      using (inputs) in { ps =>
        // need to get a fresh iterator over the cached sequence
        buildTree(ps.iterator)
      }
    }
  }
}
