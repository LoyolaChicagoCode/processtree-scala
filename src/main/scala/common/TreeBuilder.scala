package edu.luc.etl.osdi.processtree.scala.common

/**
 * The capability of converting a flat sequence of triples containing
 * PID, PPID, and command string to a map representing a process tree.
 */
trait TreeBuilder {
  def buildTree(processes: Iterator[Process]): ProcessTree
}
