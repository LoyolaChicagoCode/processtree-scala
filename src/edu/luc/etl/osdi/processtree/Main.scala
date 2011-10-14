package edu.luc.etl.osdi.processtree

import java.io.InputStream
import scala.collection.mutable._
import scala.math.{max, min}

object Main {
  
  case class Proc(pid: Int, ppid: Int, cmd: String)

  def procFromLine(header: String) = {
    val tokens = "\\S+".r.findAllIn(header).toList
    val List(iPid, iPpid, iCmd, iCommand) = 
      List("PID", "PPID", "CMD", "COMMAND").map(tokens.indexOf(_))
    val (iFirst, iSecond, iThird) = 
      (min(iPid, iPpid), max(iPid, iPpid), max(iCmd, iCommand))
    require (iPid >= 0, "required header field PID missing!")
    require (iPpid >= 0, "required header field PPID missing!")
    require (iThird > iSecond, "required header field CMD or COMMAND missing or not last!")
    val buf = new StringBuilder
    buf ++= """\S+\s+""" * iFirst ; buf ++= """(\S+)\s+"""
    buf ++= """\S+\s+""" * (iSecond - iFirst - 1) ; buf ++= """(\S+)\s+"""
    buf ++= """\S+\s+""" * (iThird - iSecond - 1) ; buf ++= """(\S.*)"""
    val pidFirst = iPid < iPpid
    val regex = buf.r
    (line: String) => {
      val regex(first, second, cmd) = line
      val (pid, ppid) = if (pidFirst) (first, second) else (second, first)
      Proc(pid.toInt, ppid.toInt, cmd)
    }
  }

  def main(args: Array[String]) = {
    val lines = scala.io.Source.fromInputStream(System.in).getLines
    val toProc = procFromLine(lines.next())

    val pmap = lines.map(l => { val p = toProc(l) ; (p.pid, p) }).toMap[Int, Proc]
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
