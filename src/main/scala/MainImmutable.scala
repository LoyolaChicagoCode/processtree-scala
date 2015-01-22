package edu.luc.etl.osdi.processtree.scala

import scala.math.{max, min}
import scala.collection.JavaConversions.enumerationAsScalaIterator
import java.io.{BufferedReader, InputStreamReader, BufferedWriter, OutputStreamWriter}

object MainImmutable {

  val IO_BUF_SIZE = 8192
  val CHILD_LIST_SIZE = 16

  def parseLine(header: String) = {
    val cols = new java.util.StringTokenizer(header).toList
    val iPid = cols indexOf "PID"
    val iPpid = cols indexOf "PPID"
    val iCmd = max(header indexOf "CMD", header indexOf "COMMAND")
    require (iPid >= 0, "required header field PID missing!")
    require (iPpid >= 0, "required header field PPID missing!")
    require (iCmd > max(iPid, iPpid), "required header field CMD or COMMAND missing or not last!")
    (line: String) => {
      val sTok = new java.util.StringTokenizer(line)
      val words = (0 to max(iPid, iPpid)).map(_ => sTok.nextToken())
      (words(iPid).toInt, words(iPpid).toInt, line.substring(iCmd))
    }
  }

  def main(args: Array[String]) = {
    val lines = scala.io.Source.fromInputStream(System.in).getLines
    val out = new BufferedWriter(new OutputStreamWriter(System.out), IO_BUF_SIZE);
    val parse = parseLine(lines.next())

    val ps = lines map parse toList
    val pmap = ps map { case (pid, _, cmd) => (pid, cmd) } toMap
    val tmap = ps groupBy (_._2)

    val start = System.currentTimeMillis

    def printTree(l: Int, i: Int) {
      for (i <- 0 until l)
    	  out.append(' ')
      out.append(i.toString)
	  out.append(": ")
	  out.append(pmap(i))
	  out.newLine();
      if (tmap.contains(i))
        tmap(i).foreach(p => printTree(l + 1, p._1))
    }

    tmap(0).foreach(p => printTree(0, p._1))
    out.flush()

    println(System.currentTimeMillis - start + " ms");
  }
}


