package mms

import java.nio.file.{Path, Paths}
import scala.io.Source

import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.{Document, Field, TextField}
import org.apache.lucene.index.{DirectoryReader, IndexWriter, IndexWriterConfig}
import org.apache.lucene.index.IndexWriterConfig.OpenMode
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.{IndexSearcher, TopDocs}
import org.apache.lucene.store.{Directory, FSDirectory}

object IndexerMain extends App {
  private def indexDictionary(writer: IndexWriter, path: Path): Unit = {
    Source
      .fromFile(path.toFile)
      .getLines()
      .map(_.trim)
      .filter(_.length > 1)
      .foreach { text =>
        val doc = new Document
        val mnemonic = MajorSystem.encode(text)
        if (mnemonic.nonEmpty) {
          doc.add(new Field("text", text, TextField.TYPE_STORED))
          doc.add(new Field("mnemonic", mnemonic, TextField.TYPE_STORED))
          writer.addDocument(doc)
        }
      }
    writer.commit()
  }

  private def printResults(topDocs: TopDocs, index: IndexSearcher): Unit = {
    val hits = topDocs.scoreDocs
    hits.foreach { hit =>
      val hitDoc = index.doc(hit.doc)
      println(
        s" * doc ${hit.doc}: ${hitDoc.get("mnemonic")} '${hitDoc.get("text")}'")
    }
  }

  val analyzer = new StandardAnalyzer()
  val directory: Directory = FSDirectory.open(Paths.get("target/index"))

  val config = new IndexWriterConfig(analyzer)
  config.setOpenMode(OpenMode.CREATE)
  val iwriter = new IndexWriter(directory, config)

  indexDictionary(iwriter, Paths.get("dictionaries/español.txt"))
//  indexDictionary(iwriter, Paths.get("dictionaries/engmix.txt"))

  val doc = new Document
  val text = "This is the text to be indexed."
  doc.add(new Field("text", text, TextField.TYPE_STORED))
  iwriter.addDocument(doc)
  iwriter.commit()
  iwriter.close()

  // Search

  val ireader = DirectoryReader.open(directory)
  val isearcher = new IndexSearcher(ireader)
  // Parse a simple query that searches for "text":
  val parser = new QueryParser("text", analyzer)

  println("'cancamusa' search:")
  printResults(isearcher.search(parser.parse("cancamusa"), 10), isearcher)

  println("42436 search:")
  printResults(isearcher.search(parser.parse("mnemonic:42436"), 10), isearcher)

  println("1995 search:")
  printResults(isearcher.search(parser.parse("mnemonic:1995"), 10), isearcher)

  private def mnemonicSearch(number: String): Stream[List[String]] = {
    def searchByMnemonic(number: String): List[String] = {
      val query = s"mnemonic:$number"
      isearcher
        .search(parser.parse(query), 10)
        .scoreDocs
        .map { hit =>
          isearcher.doc(hit.doc).get("text")
        }
        .toList
    }

    Combinatorics.allSplits(number).flatMap { parts =>
//      Combinatorics.cartesianProduct(parts.map(searchByMnemonic))
      val matches = parts.map(searchByMnemonic)
      if (matches.forall(_.nonEmpty))
        Some(matches.map(options => options.mkString("{", ", ", "}")))
      else None
    }
  }

  val number = "1004"
  println(s"Mnemonics for $number")
  mnemonicSearch(number = number).take(20).zipWithIndex.foreach {
    case (phrase, index) =>
      println(s" * ${index + 1}: ${phrase.mkString(" ")}")
  }

}
