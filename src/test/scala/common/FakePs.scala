package edu.luc.etl.osdi.processtree.scala.common

import scala.util._

/**
  * Main method to generate a fake list of processes of the specified length.
  * Also exposes its utility methods for testing/benchmarking.
  */
object FakePs extends App {

  /** Generates a barebones process tree (ppid -> pid*) of size n. */
  def fakePsMap(n: Int): Map[Int, Seq[Int]] = (2 to n).foldLeft {
    Map(0 -> Seq(1), 1 -> Seq.empty)
  } { (ps, nextPid) =>
    val randomPid = 1 + Random.nextInt(ps.keys.size - 1)
    val randomPidChildren = nextPid +: ps(randomPid)
    val nextPidChildren = ps.getOrElse(nextPid, Seq.empty)
    ps + (nextPid -> nextPidChildren, randomPid -> randomPidChildren)
  }

  /** Returns an iterator over the inverse edges of a tree represented as a multimap. */
  def inverseEdges(m: Map[Int, Seq[Int]]): Iterator[(Int, Int)] =
    for (ppid <- m.keys.iterator; pid <- m(ppid).iterator) yield (pid, ppid)

  def fakePs(n: Int, s: String): Iterator[Process] =
    inverseEdges(fakePsMap(n)).map { case (pid, ppid) => (pid, ppid, s + " " + pid)}

  def fakePs(n: Int): Iterator[Process] = fakePs(n, "Fake Process")

  val arg = Try { args(0).toInt }
  if (arg.isFailure) {
    Console.err.println("usage: fakeps n (where n = number of process table entries)")
    System.exit(1)
  }

  val n = arg.get
  val ps = fakePs(arg.get)
  println("%-10s %-10s %-10s".format("PID", "PPID", "CMD"))
  for ((pid, ppid, cmd) <- ps)
    println("%-10s %-10s %s".format(pid, ppid, cmd))
}