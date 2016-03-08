package edu.luc.etl.osdi.processtree.scala
package fakeps

import org.scalameter.api._

object Benchmark extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.exponential("processes")(1000, 1000000, 10)

  def measureMethod(f: Int => Iterator[(Int, Int)], label: String): Unit =
    measure method label in {
      using (sizes) in { n =>
        f(n)
      }
    }

//  measureMethod(fakePsFoldSlow, "fakePsFoldSlow")
//  measureMethod(fakePsFold, "fakePsFold")
//  measureMethod(fakePsMutable, "fakePsMutable")
  measureMethod(fakePsArray, "fakePsArray")
  measureMethod(fakePsArrayPar, "fakePsArrayPar")
  measureMethod(fakePsArrayTrie, "fakePsArrayTrie")
  measureMethod(fakePsArraySTM, "fakePsArraySTM")
}
