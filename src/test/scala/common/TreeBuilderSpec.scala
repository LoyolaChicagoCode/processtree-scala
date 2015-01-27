package edu.luc.etl.osdi.processtree.scala
package common

import org.scalatest.WordSpec

trait TreeBuilderSpec extends WordSpec with TreeBuilder {

  "The tree builder" when {
    "given an empty list of processes" should {
      "build the correct process tree" in {
        assert(buildTree(Iterator.empty) == Map.empty)
      }
    }

    "given a simple list of processes" should {
      "build the correct process tree" in {
        val processes = Iterator((1, 0, "cmd"))
        val tree = Map(0 -> Seq((1, 0, "cmd")))
        assert(buildTree(processes) == tree)
      }
    }

    "given a complex list of processes" should {
      "build the correct process tree" in {
        val processes = Iterator(
          (1, 0, "cmd1"), (2, 1, "cmd2"), (3, 1, "cmd3"), (4, 3, "cmd4")
        )
        val tree = Map(
          0 -> Seq((1, 0, "cmd1")),
          1 -> Seq((2, 1, "cmd2"), (3, 1, "cmd3")),
          3 -> Seq((4, 3, "cmd4"))
        )
        assert(buildTree(processes) == tree)
      }
    }
  }
}
