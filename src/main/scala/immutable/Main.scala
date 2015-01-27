package edu.luc.etl.osdi.processtree.scala
package immutable

object Main extends common.Main with Immutable

trait Immutable extends common.TreeBuilder {
  override def buildTree(processes: Iterator[(Int, Int, String)]):
  Map[Int, Seq[(Int, Int, String)]] =
    processes.toSeq groupBy { _._2 }
}
