package edu.luc.etl.osdi.processtree.scala
package fakeps

import org.scalameter.api._

object Benchmark extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.exponential("processes")(10, 10000, 10)

  def measureMethod(f: Int => Iterator[(Int, Int)], label: String): Unit =
    measure method label in {
      using (sizes) in { n =>
        f(n)
      }
    }

  measureMethod(fakePsMutable, "fakePsMutable")
  measureMethod(fakePsFold, "fakePsFold")
  measureMethod(fakePsFoldSlow, "fakePsFoldSlow")
  measureMethod(fakePsArray, "fakePsFoldArray")
}
