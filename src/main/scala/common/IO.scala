package edu.luc.etl.osdi.processtree.scala.common

import java.io.{BufferedWriter, OutputStreamWriter}

import scala.math.max

/** I/O methods for the console applications. */
trait IO {

  /**
    * Parses the header row of the output of the ps command
    * and returns a function that parses subsequent lines
    * into a triple containing PID, PPID, and command string.
    *
    * @return The function for parsing subsequent lines
    */
  def parseLine(header: String): (String) => Process = {
    val cols = header.trim.nn.split("\\s+").nn // allow to fail at runtime if Null anywhere
    val iPid = cols indexOf "PID"
    val iPpid = cols indexOf "PPID"
    val iCmd = max(header indexOf "CMD", header indexOf "COMMAND")
    require(iPid >= 0, "required header field PID missing!")
    require(iPpid >= 0, "required header field PPID missing!")
    require(iCmd > max(iPid, iPpid), "required header field CMD or COMMAND missing or not last!")
    (line: String) => {
      val sTok = new java.util.StringTokenizer(line)
      val words = (0 to max(iPid, iPpid)).map(_ => sTok.nextToken())
      (words(iPid).nn.toInt, words(iPpid).nn.toInt, line.substring(iCmd).nn)
    }
  }

  /** The buffer size for the output writer. */
  val IO_BUF_SIZE = 8192

  /** A buffered output writer for efficiency. */
  given stdout: BufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out), IO_BUF_SIZE)

  /** Prints a map representing a process tree. */
  def printTree(processTree: ProcessTree)(using out: BufferedWriter): Unit = {
    printTree(processTree, 0, 0)
    out.flush()
  }

  /** Recursively prints a map representing a process tree with indentation. */
  def printTree(processTree: ProcessTree, pid: Int, indent: Int)(using out: BufferedWriter): Unit = {
    for children <- processTree.get(pid); (cpid, _, cmd) <- children do {
      for _ <- 1 to indent do out.append(' ')
      out.append(cpid.toString)
      out.append(": ")
      out.append(cmd)
      out.newLine()
      printTree(processTree, cpid, indent + 1)
    }
  }
}
