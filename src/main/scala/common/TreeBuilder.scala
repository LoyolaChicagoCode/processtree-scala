package edu.luc.etl.osdi.processtree.scala
package common

trait TreeBuilder {
  def buildTree(processes: Iterator[(Int, Int, String)]): Map[Int, Seq[(Int, Int, String)]]
}
