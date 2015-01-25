package edu.luc.etl.osdi.processtree.scala
package common

import java.io.{BufferedWriter, OutputStreamWriter}

import scala.collection.JavaConversions.enumerationAsScalaIterator
import scala.math.max

object IO {

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

  val IO_BUF_SIZE = 8192

  val out = new BufferedWriter(new OutputStreamWriter(System.out), IO_BUF_SIZE)

  def printTree(processTree: Map[Int, Seq[(Int, Int, String)]], pid: Int = 0, indent: Int = 0) {
    for (children <- processTree.get(pid); (cpid, _, cmd) <- children) {
      for (_ <- 1 to indent) out.append(' ')
      out.append(cpid.toString)
      out.append(": ")
      out.append(cmd)
      out.newLine()
      printTree(processTree, cpid, indent + 1)
    }
    if (indent == 0) {
      out.flush()
    }
  }
}
