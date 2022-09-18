package edu.luc.etl.osdi.processtree.scala
package fakeps

import org.scalatest.funsuite.AnyFunSuite

/** Tests for the fakeps algorithms. */
class Spec extends AnyFunSuite:

  val sizes = Seq(10, 100, 1000, 10000)

  type FakePS = Int => Iterator[(Int, Int)]

  def isFlattenedTree(i: Iterator[(Int, Int)]): Boolean =
    val ps = i.toMap
    (ps.get(1) == Some(0)) && (ps - 1).values.forall(ps.contains(_))

  def testFakePs(f: FakePS, label: String): Unit =
    test(label + " should return a proper flattened ps tree") {
      for s <- sizes do
        assert(isFlattenedTree(f(s)))
    }

  testFakePs(fakePsFoldSlow, "fakePsFoldSlow")
  testFakePs(fakePsFold, "fakePsFold")
  testFakePs(fakePsArrayImmutable, "fakePsArrayImmutable")
  testFakePs(fakePsMutable, "fakePsMutable")
  testFakePs(fakePsArray, "fakePsArray")
  testFakePs(fakePsMapReduce, "fakePsMapReduce")
  testFakePs(fakePsMapReducePar, "fakePsMapReducePar")
  testFakePs(fakePsArrayPar, "fakePsArrayPar")
  testFakePs(fakePsArrayTrie, "fakePsArrayTrie")
  testFakePs(fakePsSimpleFast, "fakePsSimpleFast")

end Spec
