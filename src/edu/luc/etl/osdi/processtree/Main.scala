package edu.luc.etl.osdi.processtree

import java.io.InputStream
import scala.collection.mutable._
import scala.sys.process.{Process, ProcessIO}

object Main {

  def main(args : Array[String]) = {
    val pb = Process("ps -ef")
    val pio = new ProcessIO(_ => (), printTreeFromStream, _ => ())
    pb.run(pio)
  }
  
  case class Proc(uid: String, pid: Int, ppid: Int, c: Int, stime: String, tty: String, time: String, cmd: String)
  
  object Proc {
	implicit def fromString(s: String) = {
  	  val ProcMatch = """(\S+)\s+(\d+)\s+(\d+)\s+(\d+)\s+(\S+)\s+(\S+)\s+(\d\d:\d\d:\d\d)\s+(\S.*)""".r
	  val ProcMatch(uid, pid, ppid, c, stime, tty, time, cmd) = s
	  Proc(uid, pid.toInt, ppid.toInt, c.toInt, stime, tty, time, cmd)
	}    
  }
  
  def printTreeFromStream(in: InputStream) { 
    val lines = scala.io.Source.fromInputStream(in).getLines.drop(1)
    val pmap = lines.map(Proc.fromString).map(p => (p.pid, p)).toMap[Int, Proc]
    val tmap = new HashMap[Int, Set[Int]] with MultiMap[Int, Int]

    pmap.values.foreach(p => tmap.addBinding(p.ppid, p.pid))

    def printTree(l: Int)(i: Int) {
      println("  " * l + pmap(i))
      if (tmap.contains(i))
        tmap(i).foreach(printTree(l + 1))
    }
	
    printTree(0)(1)
  }
}
