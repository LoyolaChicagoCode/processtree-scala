package edu.luc.etl.osdi.processtree.scala
package fakeps

import scala.util.Try

/**
 * Main method to generate a fake list of processes of the specified length.
 * Also exposes its utility methods for testing/benchmarking.
 */
object Main extends App {

  val arg = Try { args(0).toInt }
  if (arg.isFailure || arg.get < 1) {
    Console.err.println("usage: fakeps n (where n > 0 = number of process table entries)")
    System.exit(1)
  }

  val ps = fakePs(arg.get)
  println("%-10s %-10s %-10s".format("PID", "PPID", "CMD"))
  for ((pid, ppid, cmd) <- ps)
    println("%-10s %-10s %s".format(pid, ppid, cmd))
}
