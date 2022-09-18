package edu.luc.etl.osdi.processtree.scala

import scala.collection.Seq

// TODO understand why this is required
// i.e. why scala.Seq or scala.collection.immutable.Seq doesn't work

/** Common type aliases. */
package object common:

  type Process = (Int, Int, String)

  type ProcessTree = Map[Int, Seq[Process]]

end common
