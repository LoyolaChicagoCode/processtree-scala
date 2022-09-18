package edu.luc.etl.osdi.processtree.scala.common

/** The common main method for the console applications. */
trait Main extends App with IO with TreeBuilder:
  val lines = scala.io.Source.stdin.getLines()
  val header = lines.next()
  val parse = parseLine(header)
  val processes = lines map parse

  val start = System.currentTimeMillis
  val processTree = buildTree(processes)
  val total = System.currentTimeMillis - start

  printTree(processTree)
  println("processing time: " + total + " ms")
end Main
