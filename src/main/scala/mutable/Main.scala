package edu.luc.etl.osdi.processtree.scala
package mutable

object Main extends common.Main with Mutable

trait Mutable extends common.TreeBuilder {

  import scala.collection.mutable.{ArrayBuffer, Buffer, HashMap}

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
