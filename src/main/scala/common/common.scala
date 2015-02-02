package edu.luc.etl.osdi.processtree.scala

/** Common type aliases. */
package object common {

  type Process = (Int, Int, String)

  type ProcessTree = Map[Int, Seq[Process]]
}
