package edu.luc.etl.osdi.processtree.scala

import scala.collection.mutable.{ArrayBuffer, Map => MMap}
import scala.util.Random
import common.Process

/**
  * Utility methods for generating a fake list of processes of the specified length.
  */
package object fakeps {

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using an immutable implementation.
    */
  def fakePsFoldSlow(n: Int): Iterator[(Int, Int)] = inverseEdges {
    (2 to n).foldLeft {
      Map(0 -> Seq(1), 1 -> Seq.empty)
    } { (ps, nextPid) =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps + (nextPid -> ps.getOrElse(nextPid, Seq.empty), randomPid -> (nextPid +: ps(randomPid)))
    }
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using an immutable implementation.
    */
  def fakePsFold(n: Int): Iterator[(Int, Int)] = inverseEdges {
    (2 to n).foldLeft {
      Map(0 -> Seq(1), 1 -> Seq.empty)
    } { (ps, nextPid) =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps + (randomPid -> (nextPid +: ps(randomPid))) + (nextPid -> Seq.empty)
    }
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a mutable implementation.
    */
  def fakePsMutable(n: Int): Iterator[(Int, Int)] = inverseEdges {
    val ps = MMap(0 -> ArrayBuffer(1), 1 -> ArrayBuffer.empty[Int])
    (2 to n) foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps(nextPid) = ArrayBuffer.empty
      ps(randomPid) += nextPid
    }
    ps.toMap
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a mutable implementation.
    */
  def fakePsArray(n: Int): Iterator[(Int, Int)] = {
    val ps = ArrayBuffer.fill(n + 1)(ArrayBuffer.empty[Int])
    ps(0) += 1
    (2 to n) foreach { nextPid =>
      ps(1 + Random.nextInt(nextPid - 1)) += nextPid
    }
    for (ppid <- (0 to n).iterator; pid <- ps(ppid).iterator) yield (pid, ppid)
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
  def fakePs(n: Int) = addCmd(fakePsArray(n))
}
