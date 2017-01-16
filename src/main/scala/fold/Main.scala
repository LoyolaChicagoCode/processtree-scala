package edu.luc.etl.osdi.processtree.scala
package fold

import common.{ Process, ProcessTree }

/** A main app that combines the common code with the mutable implementation. */
object Main extends common.Main with FoldTreeBuilder

/**
 * An immutable (purely functional) implementation of a process tree builder
 * based on `foldLeft`. This is more space-efficient than groupBy because
 * it can work directly on consecutive items of the iterator.
 */
trait FoldTreeBuilder extends common.TreeBuilder {

  override def buildTree(processes: Iterator[Process]): ProcessTree =
    processes.foldLeft(Map.empty: ProcessTree) { (m, p) =>
      val ppid = p._2
      val children = m.getOrElse(ppid, Vector.empty) :+ p
      m + (ppid -> children)
    }
}
