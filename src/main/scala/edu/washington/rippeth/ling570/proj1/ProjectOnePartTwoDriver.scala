package edu.washington.rippeth.ling570.proj1

import scala.io.Source

object ProjectOnePartTwoDriver extends App {

  // Get all lines from stdin
  val inputLinesIterator: Iterator[String] = Source.stdin.getLines

  // Split on repeated whitespace and remove empty strings
  val tokensSplitOnWhitespace: Iterator[String] = inputLinesIterator.flatMap(_.split("\\s+")).filter(_.trim.nonEmpty)

  // Create a map of strings and associated frequencies. Unseen words have a count of 0
  val counts: Map[String, Int] = tokensSplitOnWhitespace.foldLeft(Map.empty[String, Int]) { case (acc, s) =>
    acc + (s -> (acc.getOrElse(s, 0) + 1))
  }

  // Reverse sort by frequency after converting the map to a sequence of tuples
  val sortedCounts: Seq[(String, Int)]  = counts.toSeq.sortBy(-_._2)

  // For every pair, print the word and frequency with a tab
  sortedCounts.foreach{ case (w, f) => println(s"$w\t$f") }
}
