package mms

import org.scalatest.{FlatSpec, Matchers}

class MajorSystemTest extends FlatSpec with Matchers {

  "The major system" should "ignore vowels, spaces and punctuation signs" in {
    MajorSystem.encode("a e i, o: u!") shouldBe 'empty
  }

  it should "encode r and rr as 0" in {
    MajorSystem.encode("r") shouldBe "0"
    MajorSystem.encode("rr") shouldBe "0"
    MajorSystem.encode("error") shouldBe "00"
  }

  it should "encode t and d and ch as 1" in {
    MajorSystem.encode("todo") shouldBe "11"
    MajorSystem.encode("ddt") shouldBe "111"
    MajorSystem.encode("chuche") shouldBe "11"
  }

  it should "encode n and ñ as 2" in {
    MajorSystem.encode("ñoñi") shouldBe "22"
    MajorSystem.encode("nana") shouldBe "22"
  }

  it should "encode m as 3" in {
    MajorSystem.encode("mamá") shouldBe "33"
  }

  it should "encode strong c, k and q as 4" in {
    MajorSystem.encode("cacocu") shouldBe "444"
    MajorSystem.encode("eniac") shouldBe "24"
  }

  it should "encode l and ll as 5" in {
    MajorSystem.encode("llámala") shouldBe "535"
  }

  it should "encode s and x as 6" in {
    MajorSystem.encode("sex") shouldBe "66"
  }

  it should "encode f, c (soft), z as 7" in {
    MajorSystem.encode("cifazoza") shouldBe "7777"
  }

  it should "encode g and j as 8" in {
    MajorSystem.encode("gij") shouldBe "88"
  }

  it should "encode p, b and v as 9" in {
    MajorSystem.encode("abpov") shouldBe "999"
  }

  it should "be case insensitive" in {
    MajorSystem.encode("HOLA CaraCola") shouldBe "54045"
  }

  it should "write a tag for the empty word" in {
    MajorSystem.tag("") shouldBe """<code class="mms"></code>"""
  }

  it should "write tags for non-consonant letters" in {
    MajorSystem.tag("aei ou! y aa?") shouldBe """<code class="mms">aei ou! y aa?</code>"""
  }

  it should "highlight letters in tags" in {
    MajorSystem.tag("adula a Adán") shouldBe """<code class="mms">a<font color="#0000ff">d</font>u<font color="#0000ff">l</font>a a A<font color="#0000ff">d</font>á<font color="#0000ff">n</font></code>"""
    MajorSystem.tag("Adularme-se") shouldBe """<code class="mms">A<font color="#0000ff">d</font>u<font color="#0000ff">l</font>a<font color="#0000ff">rm</font>e-<font color="#0000ff">s</font>e</code>"""
    MajorSystem.tag("cacao") shouldBe """<code class="mms"><font color="#0000ff">caca</font>o</code>"""
  }
}
