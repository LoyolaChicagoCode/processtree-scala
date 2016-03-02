package edu.luc.etl.osdi.processtree.scala
package fakeps

import org.scalameter.api._

abstract class Benchmark(val f: Int => Map[Int, Iterable[Int]], val label: String) extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.exponential("processes")(10, 10000, 10)

  measure method label in {
    using (sizes) in { n =>
      f(n)
    }
  }
}

object BenchmarkMutable extends Benchmark(fakePsMutable, "fakePsMutable")

object BenchmarkFold extends Benchmark(fakePsFold, "fakePsFold")
