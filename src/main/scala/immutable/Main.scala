package edu.luc.etl.osdi.processtree.scala
package immutable

/** A main app that combines the common code with the immutable implementation. */
object Main extends common.Main with Immutable

/**
 * An immutable (purely functional) implementation of a process tree builder.
 * This is not space-efficient because it has to load the entire input into
 * memory before applying the powerful `groupBy` method.
 */
trait Immutable extends common.TreeBuilder {
  override def buildTree(processes: Iterator[(Int, Int, String)]):
  Map[Int, Seq[(Int, Int, String)]] =
    processes.toSeq groupBy { _._2 }
}

// TODO rewrite using fold and compare performance