package edu.luc.etl.osdi.processtree.scala
package immutable

object Logic {

  def buildTree(processes: Iterator[(Int, Int, String)]): Map[Int, Seq[(Int, Int, String)]] =
    processes.toSeq groupBy { _._2 }
}


