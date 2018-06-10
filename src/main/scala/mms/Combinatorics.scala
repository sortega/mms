package mms

object Combinatorics {
  def allSplits(string: String): Stream[List[String]] =
    Stream.range(1, string.length + 1).flatMap { parts =>
      allSplits(string, parts)
    }

  def allSplits(string: String, parts: Int): Stream[List[String]] = {
    require(parts > 0, s"Cannot split in $parts parts")
    require(string.nonEmpty, "Cannot split an empty string")
    if (parts == 1) Stream(List(string))
    else {
      for {
        firstPartSize <- Stream.range(1, string.length)
        (firstPart, tail) = string.splitAt(firstPartSize)
        restOfParts <- allSplits(tail, parts - 1)
      } yield firstPart :: restOfParts
    }
  }

  def cartesianProduct[E](elems: List[List[E]]): Stream[List[E]] = elems match {
    case Nil => Stream(Nil)
    case first :: rest =>
      first.toStream.flatMap { elem =>
        cartesianProduct(rest).map(elem :: _)
      }
  }
}
