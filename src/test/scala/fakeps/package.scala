package edu.luc.etl.osdi.processtree.scala
package fakeps

import scala.collection.mutable.{ArrayBuffer, Map => MMap}
import scala.util.Random
import common.Process

/**
  * Utility methods for generating a fake list of processes of the specified length.
  */
object `package` {

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using an immutable implementation. It runs very slowly.
    */
  def fakePsFold(n: Int): Map[Int, Iterable[Int]] = (2 to n).foldLeft {
    Map(0 -> Seq(1), 1 -> Seq.empty)
  } { (ps, nextPid) =>
    val randomPid = 1 + Random.nextInt(ps.size - 1)
    val randomPidChildren = nextPid +: ps(randomPid)
    val nextPidChildren = ps.getOrElse(nextPid, Seq.empty)
    ps + (nextPid -> nextPidChildren, randomPid -> randomPidChildren)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a mutable implementation. It runs blazingly fast.
    */
  def fakePsMutable(n: Int): Map[Int, Iterable[Int]] = {
    val ps = MMap(0 -> ArrayBuffer(1), 1 -> ArrayBuffer.empty[Int])
    (2 to n) foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(ps.size - 1)
      ps.getOrElseUpdate(nextPid, ArrayBuffer.empty)
      ps(randomPid) += nextPid
    }
    ps.toMap
  }

  /** Converts a tree (ppid -> pid*) into an iterator of pid -> ppid edges. */
  def inverseEdges(m: Map[Int, Iterable[Int]]): Iterator[(Int, Int)] =
    for (ppid <- m.keys.iterator; pid <- m(ppid).iterator) yield (pid, ppid)

  /** Adds a command string to each pid -> ppid edge. */
  def addCmd(i: Iterator[(Int, Int)], s: String): Iterator[Process] =
    i map { case (pid, ppid) => (pid, ppid, s + " " + pid) }

  /** Adds a command string to each pid -> ppid edge. */
  def addCmd(i: Iterator[(Int, Int)]): Iterator[Process] = addCmd(i, "Fake Process")

  /** Generates the fake ps command output. */
  def fakePs(n: Int) = addCmd(inverseEdges(fakePsMutable(n)))
}
