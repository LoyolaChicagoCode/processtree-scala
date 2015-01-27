package edu.luc.etl.osdi.processtree.scala
package common

object TreeFixtures {

  val empty = Map.empty[Int, Seq[(Int, Int, String)]]

  val simple = Map(0 -> Seq((1, 0, "cmd")))

  val complex = Map(
    0 -> Seq((1, 0, "cmd1")),
    1 -> Seq((2, 1, "cmd2"), (3, 1, "cmd3")),
    3 -> Seq((4, 3, "cmd4"))
  )
}
