package edu.luc.etl.osdi.processtree.scala

import scala.collection.mutable.{ArrayBuffer, Buffer, HashMap, MultiMap, Set}
import scala.math.{max, min}
import scala.collection.JavaConversions.enumerationAsScalaIterator
import java.io.{BufferedReader, InputStreamReader, BufferedWriter, OutputStreamWriter}

object Main {

  val IO_BUF_SIZE = 8192
  val CHILD_LIST_SIZE = 16

  case class Proc(pid: Int, ppid: Int, cmd: String)

  def procFromLine(header: String) = {
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
      Proc(words(iPid).toInt, words(iPpid).toInt, line.substring(iCmd))
    }
  }

  def main(args: Array[String]) = {
    val out = new BufferedWriter(new OutputStreamWriter(System.out), IO_BUF_SIZE);
    val in = new BufferedReader(new InputStreamReader(System.in), IO_BUF_SIZE)
    val toProc = procFromLine(in.readLine())

    val pmap = new HashMap[Int, Proc]
    val tmap = new HashMap[Int, Buffer[Int]] // with MultiMap[Int, Int]

    val start = System.currentTimeMillis

    var line = in.readLine()
    while (line != null) {
      val p = toProc(line)
      pmap += ((p.pid, p))
//      tmap.addBinding(p.ppid, p.pid)
      if (! tmap.contains(p.ppid))
        tmap += ((p.ppid, new ArrayBuffer[Int](CHILD_LIST_SIZE)))
      tmap(p.ppid) += p.pid
      line = in.readLine()
     }

    def printTree(l: Int, i: Int) {
      val p = pmap(i)
      for (i <- 0 until l)
    	  out.append(' ')
      out.append(i.toString)
	  out.append(": ")
	  out.append(p.cmd)
	  out.newLine();
      if (tmap.contains(i))
        tmap(i).foreach(printTree(l + 1, _))
    }

    tmap(0).foreach(printTree(0, _))
    out.flush()

    println(System.currentTimeMillis - start + " ms");
  }
}
