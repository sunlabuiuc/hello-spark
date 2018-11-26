package org.sunlab.example

import org.scalatest._

/**
 * This is just a dummy test to help you have a quick start
 */
class DummyTest extends FlatSpec with Matchers with Assertions {
  "This dummy test " must " test by one value" in {
    val i: Int = 10
    withClue("test i - 1") {
      i - 1 should be(9) // test change it
    }
  }

  it should " test by one collection" in {
    between(1, 5, List(2, 3, 4))
    List(1, 2, 3, 4, 5) should contain atMostOneOf (5, 6, 7)
  }

  it should " cache exceptions " in {
    intercept[IndexOutOfBoundsException] {
      "hello".charAt(-1)
    }
  }

}