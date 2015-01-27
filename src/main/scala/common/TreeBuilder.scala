package edu.luc.etl.osdi.processtree.scala.common

trait TreeBuilder {
  def buildTree(processes: Iterator[(Int, Int, String)]): Map[Int, Seq[(Int, Int, String)]]
}
