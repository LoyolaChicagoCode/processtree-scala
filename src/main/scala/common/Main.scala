package edu.luc.etl.osdi.processtree.scala.common

trait Main extends App with IO with TreeBuilder {

  override def main(args: Array[String]): Unit = {
    val lines = scala.io.Source.stdin.getLines
    val header = lines.next()
    val parse = parseLine(header)
    val processes = lines map parse

    val start = System.currentTimeMillis
    val processTree = buildTree(processes)
    val total = System.currentTimeMillis - start

    printTree(processTree)
    println("processing time: " + total + " ms")
  }
}
