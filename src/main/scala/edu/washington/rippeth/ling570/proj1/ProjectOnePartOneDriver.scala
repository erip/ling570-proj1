package edu.washington.rippeth.ling570.proj1

import scala.io.Source

object ProjectOnePartOneDriver extends App {

  implicit val abbreviations: Set[String] = Source.fromFile(args.head).getLines.toSet

  val inputLinesIterator: Iterator[String] = Source.stdin.getLines

  val tokenizedLines: Iterator[String] = inputLinesIterator.map(EnglishTokenizer.tokenize)

  tokenizedLines.foreach(println)
}
