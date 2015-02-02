package edu.luc.etl.osdi.processtree.scala
package groupby

import common.{Process, ProcessTree}

/** A main app that combines the common code with the immutable implementation. */
object Main extends common.Main with GroupByTreeBuilder

/**
 * An immutable (purely functional) implementation of a process tree builder.
 * This is not space-efficient because it has to load the entire input into
 * memory before applying the powerful `groupBy` method.
 */

trait GroupByTreeBuilder extends common.TreeBuilder {
  override def buildTree(processes: Iterator[Process]): ProcessTree =
    processes.toSeq groupBy { _._2 }
}
