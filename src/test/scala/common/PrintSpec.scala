package edu.luc.etl.osdi.processtree.scala
package common

import java.io.{BufferedWriter, StringWriter}
import org.scalatest.wordspec.AnyWordSpec

/** Tests for the `printTree` method. */
class PrintSpec extends AnyWordSpec with IO:

  val EOL = System.lineSeparator

  def swToBw(sw: StringWriter) = new BufferedWriter(sw, IO_BUF_SIZE)

  def fixture() = new StringWriter

  "The string writer" when:
    "when used through a buffered writer" should:
      "return the output as a string" in:
        val f = fixture()
        val bw = swToBw(f)
        bw.append("asdf")
        bw.flush()
        assert(f.toString == "asdf")

  "The tree printer" when:
    "given an empty tree" should:
      "print this tree correctly" in:
        val f = fixture()
        given BufferedWriter = swToBw(f)
        printTree(TreeFixtures.empty)
        assert(f.toString == "")

    "given a simple tree" should:
      "print this tree correctly" in:
        val f = fixture()
        given BufferedWriter = swToBw(f)
        printTree(TreeFixtures.simple)
        assert(f.toString == "1: cmd" + EOL)

    "given a complex tree" should:
      "print this tree correctly" in:
        val f = fixture()
        given BufferedWriter = swToBw(f)
        printTree(TreeFixtures.complex)
        assert(f.toString ==
          """|1: cmd1
             | 2: cmd2
             | 3: cmd3
             |  4: cmd4
             |""".stripMargin)

end PrintSpec
