package edu.luc.etl.osdi.processtree.scala
package mutable

import edu.luc.etl.osdi.processtree.scala.common._

import scala.collection.mutable.{ArrayBuffer, Buffer, HashMap}

object Main extends App {
  val lines = scala.io.Source.stdin.getLines
  val parse = IO.parseLine(lines.next())
  val processes = lines map parse
  
  val start = System.currentTimeMillis
  val processTree = Mutable.buildTree(processes)
  IO.printTree(processTree)

  println("processing time: " + System.currentTimeMillis - start  + " ms")
}

object Mutable extends TreeBuilder {
  val CHILD_LIST_SIZE = 16
  override def buildTree(processes: Iterator[(Int, Int, String)]): Map[Int, scala.Seq[(Int, Int, String)]] = {
    val treeMap = new HashMap[Int, Buffer[Tuple3[Int, Int, String]]]
    while (processes.hasNext) {
      val tuple = processes.next()
      val (pid, ppid, cmd) = tuple
      if (! treeMap.contains(ppid)) {
        treeMap += ((ppid, new ArrayBuffer[Tuple3[Int, Int, String]](CHILD_LIST_SIZE)))
      }
      treeMap(ppid) += tuple
    }
    treeMap.toMap
  }
}
