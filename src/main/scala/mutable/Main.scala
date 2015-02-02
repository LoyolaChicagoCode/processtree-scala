package edu.luc.etl.osdi.processtree.scala
package mutable

import common.{Process, ProcessTree}

/** A main app that combines the common code with the mutable implementation. */
object Main extends common.Main with MutableTreeBuilder

/** A mutable (imperative) implementation of a process tree builder. */
trait MutableTreeBuilder extends common.TreeBuilder {

  import scala.collection.mutable.{ArrayBuffer, Buffer, HashMap}

  val CHILD_LIST_SIZE = 16

  override def buildTree(processes: Iterator[Process]): ProcessTree = {
    val treeMap = new HashMap[Int, Buffer[Process]]
    while (processes.hasNext) {
      val tuple = processes.next()
      val ppid = tuple._2
      if (! treeMap.contains(ppid)) {
        treeMap(ppid) = new ArrayBuffer[Process](CHILD_LIST_SIZE)
      }
      treeMap(ppid) += tuple
    }
    treeMap.toMap
  }
}
