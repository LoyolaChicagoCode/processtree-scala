package edu.luc.etl.osdi.processtree.scala
package immutable

import common._
import IO._

object Main extends App {
  val lines = scala.io.Source.stdin.getLines
  val header = lines.next()
  val parse = IO.parseLine(header)
  val processes = lines map parse

  val start = System.currentTimeMillis
  val processTree = Immutable.buildTree(processes)
  val total = System.currentTimeMillis - start

  printTree(processTree)
  println("processing time: " + total + " ms")
}

object Immutable extends TreeBuilder {
  override def buildTree(processes: Iterator[(Int, Int, String)]):
  Map[Int, Seq[(Int, Int, String)]] =
    processes.toSeq groupBy { _._2 }
}
