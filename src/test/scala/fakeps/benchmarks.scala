package edu.luc.etl.osdi.processtree.scala
package fakeps

import org.scalameter.api._

abstract class Benchmark(val f: Int => Iterator[(Int, Int)], val label: String) extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.exponential("processes")(10, 1000000, 10)

  measure method label in {
    using (sizes) in { n =>
      f(n)
    }
  }
}

object BenchmarkMutable extends Benchmark(fakePsMutable, "fakePsMutable")

object BenchmarkFold extends Benchmark(fakePsFold, "fakePsFold")

object BenchmarkFoldSlow extends Benchmark(fakePsFoldSlow, "fakePsFoldSlow")

object BenchmarkArray extends Benchmark(fakePsArray, "fakePsFoldSlow")
