package edu.luc.etl.osdi.processtree.scala

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import common.Process

/**
  * Utility methods for generating a fake list of processes of the specified length.
  */
package object fakeps {

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using an immutable implementation. Unacceptably slow
    * because of a bug in Map.+(vararg).
    */
  def fakePsFoldSlow(n: Int): Iterator[(Int, Int)] = reverseEdges {
    (2 to n).foldLeft {
      Map(0 -> Seq(1), 1 -> Seq.empty)
    } { (ps, nextPid) =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps + (randomPid -> (nextPid +: ps(randomPid)), nextPid -> Seq.empty)
    }
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using an immutable implementation.
    */
  def fakePsFold(n: Int): Iterator[(Int, Int)] = reverseEdges {
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
  def fakePsMutable(n: Int): Iterator[(Int, Int)] = reverseEdges {
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
    * using a mutable implementation.
    */
  def fakePsArray(n: Int): Iterator[(Int, Int)] = {
    val ps = Vector.fill(n + 1)(ArrayBuffer.empty[Int])
    ps(0) += 1
    (2 to n) foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps(randomPid) += nextPid
    }
    for (ppid <- ps.indices.iterator ; pid <- ps(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a mutable implementation with a parallel range and a
    * concurrent queue (from Java).
    */
  def fakePsArrayPar(n: Int): Iterator[(Int, Int)] = {
    import java.util.concurrent.ConcurrentLinkedQueue
    import scala.collection.JavaConversions._
    val ps = Vector.fill(n + 1)(new ConcurrentLinkedQueue[Int])
    ps(0) add 1
    (2 to n).par foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps(randomPid) add nextPid
    }
    for (ppid <- ps.indices.iterator ; pid <- ps(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a mutable implementation with a parallel range and a lock-free trie.
    */
  def fakePsArrayTrie(n: Int): Iterator[(Int, Int)] = {
    import scala.collection.concurrent.TrieMap
    val ps = Vector.fill(n + 1)(TrieMap.empty[Int, Unit])
    ps(0) += (1 -> (()))
    (2 to n).par foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      ps(randomPid) += (nextPid -> (()))
    }
    for (ppid <- ps.indices.iterator ; (pid, _) <- ps(ppid).iterator) yield (pid, ppid)
  }

  /**
    * Generates a barebones process tree (ppid -> pid*) of size n
    * using a mutable implementation with a parallel range and STM.
    */
  def fakePsArraySTM(n: Int): Iterator[(Int, Int)] = {
    import scala.concurrent.stm._
    val ps = Vector.fill(n + 1)(TSet.empty[Int])
    atomic { implicit tx => ps(0) += 1 }
    (2 to n).par foreach { nextPid =>
      val randomPid = 1 + Random.nextInt(nextPid - 1)
      atomic { implicit tx => ps(randomPid) += nextPid }
    }
    for (ppid <- ps.indices.iterator ; pid <- ps(ppid).single.iterator) yield (pid, ppid)
  }

  /** Converts a tree (ppid -> pid*) into an iterator of pid -> ppid edges. */
  def reverseEdges(m: Map[Int, Iterable[Int]]): Iterator[(Int, Int)] =
    for (ppid <- m.keys.iterator ; pid <- m(ppid).iterator) yield (pid, ppid)

  /** Adds a command string to each pid -> ppid edge. */
  def addCmd(i: Iterator[(Int, Int)], s: String): Iterator[Process] =
    i map { case (pid, ppid) => (pid, ppid, s + " " + pid) }

  /** Adds a command string to each pid -> ppid edge. */
  def addCmd(i: Iterator[(Int, Int)]): Iterator[Process] = addCmd(i, "Fake Process")

  /** Generates the fake ps command output. */
  def fakePs(n: Int) = addCmd(fakePsArraySTM(n))
}
