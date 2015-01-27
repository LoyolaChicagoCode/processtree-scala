package edu.luc.etl.osdi.processtree.scala
package common

import java.io.{BufferedWriter, StringWriter}

import org.scalatest.WordSpec

class PrintSpec extends WordSpec with IO {

  def fixture() = new {
    val stringWriter = new StringWriter
    implicit val strout = new BufferedWriter(stringWriter, IO_BUF_SIZE)
  }

  "The string writer" when {
    "when used through a buffered writer" should {
      "return the output as a string" in {
        val f = fixture()
        f.strout.append("asdf")
        f.strout.flush()
        assert(f.stringWriter.toString == "asdf")
      }
    }
  }

  "The tree printer" when {
    "given an empty tree" should {
      "print this tree correctly" in {
        val f = fixture()
        printTree(TreeFixtures.empty)(f.strout)
        assert(f.stringWriter.toString == "")
      }
    }

    "given a simple tree" should {
      "print this tree correctly" in {
        val f = fixture()
        printTree(TreeFixtures.simple)(f.strout)
        assert(f.stringWriter.toString == "1: cmd\n")
      }
    }

    "given a complex tree" should {
      "print this tree correctly" in {
        val f = fixture()
        printTree(TreeFixtures.complex)(f.strout)
        assert(f.stringWriter.toString ==
          """|1: cmd1
             | 2: cmd2
             | 3: cmd3
             |  4: cmd4
             |""".stripMargin)
      }
    }
  }
}
