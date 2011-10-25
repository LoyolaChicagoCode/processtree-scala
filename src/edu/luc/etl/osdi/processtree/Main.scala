package edu.luc.etl.osdi.processtree

import java.io.InputStream
import scala.collection.mutable._
import scala.math.{max, min}

object Main {
  
  case class Proc(pid: Int, ppid: Int, cmd: String)

  def procFromLine(header: String) = {
    val cols = header.trim.split("\\s+")
    val iPid = cols indexOf "PID"
    val iPpid = cols indexOf "PPID"
    val iCmd = max(header indexOf "CMD", header indexOf "COMMAND")
    require (iPid >= 0, "required header field PID missing!")
    require (iPpid >= 0, "required header field PPID missing!")
    require (iCmd > max(iPid, iPpid), "required header field CMD or COMMAND missing or not last!")
    (line: String) => {
//      val words = line.substring(0, iCmd).trim.split("\\s+")
      val sTok = new java.util.StringTokenizer(line)
      val words = (0 to max(iPid, iPpid)).map(_ => sTok.nextToken())
      Proc(words(iPid).toInt, words(iPpid).toInt, line.substring(iCmd))
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
