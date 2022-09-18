package edu.luc.etl.osdi.processtree.scala
package common

import org.scalatest.wordspec.AnyWordSpec

/** Tests for the tree-building algorithm. */
abstract class TreeBuilderSpec(label: String) extends AnyWordSpec with TreeBuilder:

  given CanEqual[ProcessTree, ProcessTree] = CanEqual.derived

  "The " + label + " tree builder" when {
    "given an empty list of processes" should {
      "build the correct process tree" in {
        assert(buildTree(Iterator.empty) == TreeFixtures.empty)
      }
    }

    "given a simple list of processes" should {
      "build the correct process tree" in {
        val processes = Iterator((1, 0, "cmd"))
        assert(buildTree(processes) == TreeFixtures.simple)
      }
    }

    "given a complex list of processes" should {
      "build the correct process tree" in {
        val processes = Iterator(
          (1, 0, "cmd1"), (2, 1, "cmd2"), (3, 1, "cmd3"), (4, 3, "cmd4")
        )
        assert(buildTree(processes) == TreeFixtures.complex)
      }
    }
  }

end TreeBuilderSpec
