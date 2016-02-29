package edu.luc.etl.osdi.processtree.scala.common

import org.scalameter.api._

/** Microbenchmark for `buildTree`. */
abstract class Benchmark(label: String) extends Bench.LocalTime with TreeBuilder {

  val sizes: Gen[Int] = Gen.exponential("processes")(100, 10000, 10)

  val inputs: Gen[Iterator[Process]] = sizes map FakePs.fakePs

  performance of label in {
    measure method "fakePs" in {
      using(inputs) in { ps =>
        buildTree(ps)
      }
    }
  }
}
