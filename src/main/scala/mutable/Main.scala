package edu.luc.etl.osdi.processtree.scala
package mutable

import common.{Process, ProcessTree}

/** A main app that combines the common code with the mutable implementation. */
object Main extends common.Main with MutableTreeBuilder

/** A mutable (imperative) implementation of a process tree builder. */
trait MutableTreeBuilder extends common.TreeBuilder:

  import scala.collection.mutable.{ListBuffer, HashMap}

  override def buildTree(processes: Iterator[Process]): ProcessTree =
    val treeMap = new HashMap[Int, ListBuffer[Process]]
    while processes.hasNext do
      val tuple = processes.next()
      val ppid = tuple._2
      if !treeMap.contains(ppid) then
        treeMap(ppid) = ListBuffer.empty
      treeMap(ppid) += tuple
    treeMap.toMap
  
end MutableTreeBuilder
