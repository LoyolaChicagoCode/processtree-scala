package edu.luc.etl.osdi.processtree.scala
package mutable

import common._
import IO._

import scala.collection.mutable.{ArrayBuffer, Buffer, HashMap}

object Main extends App {
  val lines = scala.io.Source.stdin.getLines
  val parse = IO.parseLine(lines.next())
  val processes = lines map parse
  
  val start = System.currentTimeMillis
  val processTree = Mutable.buildTree(processes)
  val total = System.currentTimeMillis - start

  printTree(processTree)
  println("processing time: " + total + " ms")
}

object Mutable extends TreeBuilder {

  val CHILD_LIST_SIZE = 16

  override def buildTree(processes: Iterator[(Int, Int, String)]):
  Map[Int, scala.Seq[(Int, Int, String)]] = {
    val treeMap = new HashMap[Int, Buffer[(Int, Int, String)]]
    while (processes.hasNext) {
      val tuple = processes.next()
      val (pid, ppid, cmd) = tuple
      if (! treeMap.contains(ppid)) {
        treeMap += ((ppid, new ArrayBuffer[(Int, Int, String)](CHILD_LIST_SIZE)))
      }
      treeMap(ppid) += tuple
    }
    treeMap.toMap
  }
}
