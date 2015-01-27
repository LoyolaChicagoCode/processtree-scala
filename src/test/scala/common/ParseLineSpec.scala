package edu.luc.etl.osdi.processtree.scala
package common

import org.scalatest.WordSpec

class ParseLineSpec extends WordSpec with IO {

  "The line parser" when {
    "given an empty header" should {
      "reject this argument" in {
        intercept[IllegalArgumentException] {
          parseLine("")
        }
      }
    }

    "given a header without PID" should {
      "reject this argument" in {
        intercept[IllegalArgumentException] {
          parseLine("PPID CMD")
        }
      }
    }

    "given a header without PPID" should {
      "reject this argument" in {
        intercept[IllegalArgumentException] {
          parseLine("PID CMD")
        }
      }
    }

    "given a header without CMD" should {
      "reject this argument" in {
        intercept[IllegalArgumentException] {
          parseLine("PPID PID")
        }
      }
    }

    "given an simple header" should {
      val parser = parseLine("PPID PID CMD")

      "reject an empty line" in {
        intercept[RuntimeException] {
          parser("")
        }
      }

      "reject an invalid line" in {
        intercept[RuntimeException] {
          parser("")
        }
      }

      "parse a valid line" in {
        //             PPID PID CMD
        assert(parser("1    2   asdf") == (2, 1, "asdf"))
      }
    }

    "given an complex header" should {
      val parser = parseLine("i1 PPID i2 PID i3 CMD")

      "reject an empty line" in {
        intercept[RuntimeException] {
          parser("")
        }
      }

      "reject an invalid line" in {
        intercept[RuntimeException] {
          parser("")
        }
      }

      "parse a valid line" in {
        //             i1 PPID i2 PID i3 CMD
        assert(parser("i1 1    i2 2   i3 asdf") == (2, 1, "asdf"))
      }
    }

    "given an actual header" should {
      val parser = parseLine("  UID   PID  PPID   C STIME   TTY           TIME CMD")

      "reject an empty line" in {
        intercept[RuntimeException] {
          parser("")
        }
      }

      "reject an invalid line" in {
        intercept[RuntimeException] {
          parser("    0    17     1   0 0:27.50 /usr/sbin/syslogd")
        }
      }

      "parse a valid line" in {
        assert(parser("    0    17     1   0 Thu04PM ??         0:27.50 /usr/sbin/syslogd arg1 arg2") == (17, 1, "/usr/sbin/syslogd arg1 arg2"))
      }
    }
  }
}
