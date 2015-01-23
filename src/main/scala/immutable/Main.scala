package edu.luc.etl.osdi.processtree.scala
package immutable

object Main extends App {

  val lines = scala.io.Source.stdin.getLines
  val header = lines.next()
  val parse = IO.parseLine(header)
  val processes = lines map parse

  val start = System.currentTimeMillis
  val processTree = Logic.buildTree(processes)
  val total = System.currentTimeMillis - start

  IO.printTree(processTree)
  println("processing time: " + total + " ms")
}


