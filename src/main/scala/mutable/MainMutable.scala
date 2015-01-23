package edu.luc.etl.osdi.processtree.scala
package mutable

import java.io.{BufferedWriter, OutputStreamWriter}

import scala.collection.JavaConversions.enumerationAsScalaIterator
import scala.collection.mutable.{ArrayBuffer, Buffer, HashMap}
import scala.math.max

object MainMutable {

  val IO_BUF_SIZE = 8192
  val CHILD_LIST_SIZE = 16

  def parseLine(header: String) = {
    val cols = new java.util.StringTokenizer(header).toList
    val iPid = cols indexOf "PID"
    val iPpid = cols indexOf "PPID"
    val iCmd = max(header indexOf "CMD", header indexOf "COMMAND")
    require(iPid >= 0, "required header field PID missing!")
    require(iPpid >= 0, "required header field PPID missing!")
    require(iCmd > max(iPid, iPpid), "required header field CMD or COMMAND missing or not last!")
    (line: String) => {
      val sTok = new java.util.StringTokenizer(line)
      val words = (0 to max(iPid, iPpid)).map(_ => sTok.nextToken())
      (words(iPid).toInt, words(iPpid).toInt, line.substring(iCmd))
    }
  }

  def main(args: Array[String]) = {
    val lines = scala.io.Source.stdin.getLines
    val out = new BufferedWriter(new OutputStreamWriter(System.out), IO_BUF_SIZE);
    val parse = parseLine(lines.next())

    val pmap = new HashMap[Int, String]
    val tmap = new HashMap[Int, Buffer[Int]]

    val start = System.currentTimeMillis

    for (line <- lines) {
      val (pid, ppid, cmd) = parse(line)
      pmap += ((pid, cmd))
      if (! tmap.contains(ppid))
        tmap += ((ppid, new ArrayBuffer[Int](CHILD_LIST_SIZE)))
      tmap(ppid) += pid
    }

    def printTree(l: Int, i: Int) {
       (0 until l) foreach { _ => out.append(' ') }
      out.append(i.toString)
	  out.append(": ")
	  out.append(pmap(i))
	  out.newLine();
      if (tmap.contains(i))
        tmap(i) foreach { printTree(l + 1, _) }
    }

    tmap(0) foreach { printTree(0, _) }
    out.flush()

    println(System.currentTimeMillis - start + " ms");
  }
}
