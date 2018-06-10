package mms

import org.scalatest.{FlatSpec, Matchers}

class CombinatoricsTest extends FlatSpec with Matchers {

  "Splitting a string in one part" should "do nothing" in {
    Combinatorics.allSplits("hola", parts = 1) shouldBe Stream(List("hola"))
  }

  "Splitting a string into two parts" should "word" in {
    Combinatorics.allSplits("1995", parts = 2).toSet shouldBe Set(
      List("1", "995"),
      List("19", "95"),
      List("199", "5")
    )
  }

  "The cartesian product" should "work" in {
    Combinatorics.cartesianProduct(Nil) shouldBe Stream(Nil)
    Combinatorics.cartesianProduct(List(List(1, 2))) shouldBe List(List(1),
                                                                   List(2))
    Combinatorics
      .cartesianProduct(List(List(1, 2), List(10, 20)))
      .toSet shouldBe Set(List(1, 10), List(1, 20), List(2, 10), List(2, 20))
  }
}
