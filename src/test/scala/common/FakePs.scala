package edu.luc.etl.osdi.processtree.scala.common

import scala.util._

object FakePs extends App {
  
  def fakePs(n: Int): Map[Int, Seq[Int]] = (2 to arg.get) .foldLeft { 
    Map(0 -> Seq(1), 1 -> Seq.empty)
  } { (ps, nextPid) =>
    val randomPid = 1 + Random.nextInt(ps.keys.size - 1)
    val randomPidChildren = nextPid +: ps(randomPid)
    val nextPidChildren = ps.getOrElse(nextPid, Seq.empty) 
    ps + (nextPid -> nextPidChildren, randomPid -> randomPidChildren)
  }
  
  val arg = Try { args(0).toInt }
  if (arg.isFailure) {
    Console.err.println("usage: fakeps n (where n = number of process table entries)")
    System.exit(1)
  }

  val ps = fakePs(arg.get)
  
  println("%-10s %-10s %-10s".format("PID","PPID","CMD"))
  val pids = for (pid <- ps.keys.toSeq ; cpid <- ps(pid)) yield (cpid, pid)
  for ((cpid, pid) <- pids.sorted)
    println("%-10s %-10s Fake Process %s".format(cpid, pid, cpid))
}