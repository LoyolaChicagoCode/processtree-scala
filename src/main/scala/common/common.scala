package edu.luc.etl.osdi.processtree.scala.common

import scala.collection.Seq

// TODO understand why this is required
// i.e. why scala.Seq or scala.collection.immutable.Seq doesn't work

/** Common type aliases. */
type Process = (Int, Int, String)

type ProcessTree = Map[Int, Seq[Process]]
