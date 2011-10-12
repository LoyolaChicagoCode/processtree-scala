package edu.luc.etl.osdi.processtree

import java.io.InputStream
import scala.collection.mutable._
import scala.math.max

object Main {
  
  case class Proc(pid: Int, ppid: Int, cmd: String)

  val regex = """\S+""".r
  
  def analyzeHeader(header: String) = {
    val tokens = regex.findAllIn(header).toList
    (tokens.indexOf("PID"), tokens.indexOf("PPID"), max(tokens.indexOf("CMD"), tokens.indexOf("COMMAND")))
  }

  def parseLine(line: String, indices: (Int, Int, Int)) = {
    val tokens = regex.findAllIn(line).toList
    Proc(tokens(indices._1).toInt, tokens(indices._2).toInt, tokens.drop(indices._3).mkString(" "))
  }

  def main(args: Array[String]) = {
    val lines = scala.io.Source.fromInputStream(System.in).getLines
    val header = lines.next()
    val indices = analyzeHeader(header)

    val pmap = lines.map(l => { val p = parseLine(l, indices) ; (p.pid, p) }).toMap[Int, Proc]
    val tmap = new HashMap[Int, Set[Int]] with MultiMap[Int, Int]
    pmap.values.foreach(p => tmap.addBinding(p.ppid, p.pid))

    def printTree(l: Int)(i: Int) {
      val p = pmap(i)
      printf("%s%d: %s\n", " " * l, p.pid, p.cmd)
      if (tmap.contains(i))
        tmap(i).foreach(printTree(l + 1))
    }
	
    tmap(0).foreach(printTree(0)(_))
  }
}
