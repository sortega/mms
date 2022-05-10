package mms

object MajorSystem {

  def encode(text: String): String = {
    val result = new StringBuffer
    val it = text.toLowerCase.iterator.buffered
    while (it.nonEmpty) {
      it.next() match {
        case 'r' =>
          result.append('0')
          if (it.nonEmpty && it.head == 'r') it.next()

        case 't' | 'd' => result.append('1')

        case 'c' if it.nonEmpty && it.head == 'h' => result.append('1')
        case 'c' if it.nonEmpty && (it.head == 'e' || it.head == 'i') =>
          result.append('7')
        case 'c' => result.append('4')

        case 'n' | 'ñ' => result.append('2')

        case 'm' => result.append('3')

        case 'k' | 'q' => result.append('4')

        case 'l' =>
          result.append('5')
          if (it.nonEmpty && it.head == 'l') it.next()

        case 's' | 'x' => result.append('6')

        case 'f' | 'z' => result.append('7')

        case 'g' | 'j' => result.append('8')

        case 'p' | 'b' | 'v' => result.append('9')

        case _ =>
      }
    }
    result.toString
  }

  def tag(text: String): String = {
    val pattern = """(?i)(rr?|ll?|c[aeiouáéíóúh]?|[tdmnñkqsxfzgjpbv])+""".r
    val highlightedText = pattern.replaceAllIn(
      text,
      matched => s"""<font color="#0000ff">$matched</font>""")
    s"""<code class="mms">$highlightedText</code>"""
  }
}
