package edu.luc.etl.osdi.processtree.scala

import scala.collection.mutable.ArrayBuffer
import scala.collection.GenIterable
import scala.util.Random
import common.Process

/** Utility methods for generating a fake list of processes of the specified length. */
package object fakeps {

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using an immutable map. Unacceptably slow
    * because of a bug in Map.+(vararg).
    */
  def fakePsFoldSlow(n: Int): Iterator[(Int, Int)] = reverseEdges {
    require { n > 0 }
    (2 to n).foldLeft {
      Map(0 -> Seq(1), 1 -> Seq.empty)
    } { (ps, nextPid) =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps + (randomPid -> (nextPid +: ps(randomPid)), nextPid -> Seq.empty)
    }
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using an immutable map.
    */
  def fakePsFold(n: Int): Iterator[(Int, Int)] = reverseEdges {
    require { n > 0 }
    (2 to n).foldLeft {
      Map(0 -> Seq(1), 1 -> Seq.empty)
    } { (ps, nextPid) =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps + (randomPid -> (nextPid +: ps(randomPid))) + (nextPid -> Seq.empty)
    }
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a preallocated immutable vector of immutable vectors.
    */
  def fakePsArrayImmutable(n: Int): Iterator[(Int, Int)] = {
    require { n > 0 }
    val ps0 = Vector.fill(n + 1)(Vector.empty[Int])
    val ps1 = ps0.updated(0, Vector(1))
    val ps = (2 to n).foldLeft(ps1) { (ps, nextPid) =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps.updated(randomPid, ps(randomPid) :+ nextPid)
    }
    for (ppid <- ps.indices.iterator; pid <- ps(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a mutable map.
    */
  def fakePsMutable(n: Int): Iterator[(Int, Int)] = reverseEdges {
    require { n > 0 }
    import scala.collection.mutable.Map
    val ps = Map(0 -> ArrayBuffer(1), 1 -> ArrayBuffer.empty[Int])
    (2 to n) foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps(nextPid) = ArrayBuffer.empty
      ps(randomPid) += nextPid
    }
    ps.toMap
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a preallocated immutable vector of mutable array-based lists.
    */
  def fakePsArray(n: Int): Iterator[(Int, Int)] = {
    require { n > 0 }
    val ps = Vector.fill(n + 1)(ArrayBuffer.empty[Int])
    ps(0) += 1
    (2 to n) foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps(randomPid) += nextPid
    }
    for (ppid <- ps.indices.iterator; pid <- ps(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using map-reduce over sequential collections.
    */
  def fakePsMapReduce(n: Int): Iterator[(Int, Int)] = {
    require { n > 0 }
    val ps1 = (2 to n) map { nextPid => (1 + Random.nextInt(nextPid - 1), nextPid) }
    val ps2 = (Seq((0, 1)) ++ ps1) groupBy { _._1 }
    for (ppid <- ps2.keys.iterator; (_, pid) <- ps2(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using map-reduce over parallel collections.
    */
  def fakePsMapReducePar(n: Int): Iterator[(Int, Int)] = {
    require { n > 0 }
    val ps1 = (2 to n).par map { nextPid => (1 + Random.nextInt(nextPid - 1), nextPid) }
    val ps2 = (Seq((0, 1)).par ++ ps1) groupBy { _._1 }
    for (ppid <- ps2.keys.iterator; (_, pid) <- ps2(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a preallocated immutable vector of with a parallel range and
    * mutable concurrent queues (from Java).
    */
  def fakePsArrayPar(n: Int): Iterator[(Int, Int)] = {
    require { n > 0 }
    import java.util.concurrent.ConcurrentLinkedQueue
    import scala.collection.JavaConversions._
    val ps = Vector.fill(n + 1)(new ConcurrentLinkedQueue[Int])
    ps(0) add 1
    (2 to n).par foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps(randomPid) add nextPid
    }
    for (ppid <- ps.indices.iterator; pid <- ps(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a preallocated immutable vector of with a parallel range and
    * lock-free tries.
    */
  def fakePsArrayTrie(n: Int): Iterator[(Int, Int)] = {
    require { n > 0 }
    import scala.collection.concurrent.TrieMap
    val ps = Vector.fill(n + 1)(TrieMap.empty[Int, Unit])
    ps(0) += (1 -> (()))
    (2 to n).par foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps(randomPid) += (nextPid -> (()))
    }
    for (ppid <- ps.indices.iterator; (pid, _) <- ps(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a preallocated immutable vector of with a parallel range and
    * STM-based mutable sets.
    */
  def fakePsArraySTM(n: Int): Iterator[(Int, Int)] = {
    require { n > 0 }
    import scala.concurrent.stm._
    val ps = Vector.fill(n + 1)(TSet.empty[Int])
    atomic { implicit tx => ps(0) += 1 }
    (2 to n).par foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      atomic { implicit tx => ps(randomPid) += nextPid }
    }
    for (ppid <- ps.indices.iterator; pid <- ps(ppid).single.iterator) yield (pid, ppid)
  }

  /**
    * Simply enumerates the child-parent edges of a barebones process tree (ppid -> pid*)
    * of size n. This should work in constant space.
    */
  def fakePsSimpleFast(n: Int): Iterator[(Int, Int)] = {
    require { n > 0 }
    Iterator(1 -> 0) ++ ((2 to n).toIterator map { cpid => cpid -> (1 + Random.nextInt(cpid - 1)) })
  }

  /** Converts a tree (ppid -> pid*) into an iterator of pid -> ppid edges. */
  def reverseEdges(m: Map[Int, GenIterable[Int]]): Iterator[(Int, Int)] =
    for (ppid <- m.keys.iterator; pid <- m(ppid).iterator) yield (pid, ppid)

  /** Adds a command string to each pid -> ppid edge. */
  def addCmd(i: Iterator[(Int, Int)], s: String): Iterator[Process] =
    i map { case (pid, ppid) => (pid, ppid, s + " " + pid) }

  /** Adds a command string to each pid -> ppid edge. */
  def addCmd(i: Iterator[(Int, Int)]): Iterator[Process] = addCmd(i, "Fake Process")

  /** Generates the fake ps command output. */
  def fakePs(n: Int) = addCmd(fakePsMapReduce(n))
}
