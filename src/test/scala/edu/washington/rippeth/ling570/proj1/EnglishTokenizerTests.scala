package edu.washington.rippeth.ling570.proj1

import org.scalatest.WordSpecLike

class EnglishTokenizerTests extends WordSpecLike {

  protected def tokenize(line: String)(implicit abbrevs: Set[String]): String = EnglishTokenizer.tokenize(line)

  "An EnglishTokenizer" should {
    "respect abbreviations" when {
      "the set of abbreviations is non-empty" in {
        implicit val abbrevs: Set[String] = Set("Mr.", "Dr.")
        val line = "Mr. Rippeth knows Dr. Xia."
        val expectedTokens = Seq("Mr.", "Rippeth", "knows", "Dr.", "Xia", ".")
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }
      "they are followed by a comma" in {
        implicit val abbrevs: Set[String] = Set.empty[String]
        val line = "Have you been to N.Y., sir?"
        val expectedTokens = Seq("Have", "you", "been", "to", "N.Y.", ",", "sir", "?")
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }
      "they are followed by other punctuation" in {
        implicit val abbrevs: Set[String] = Set.empty[String]
        val line = "Have you been to N.Y.?"
        val expectedTokens = Seq("Have", "you", "been", "to", "N.Y.", "?")
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }
      "they are not followed by other punctuation" in {
        implicit val abbrevs: Set[String] = Set.empty[String]
        val line = "Have you been to N.Y. lately?"
        val expectedTokens = Seq("Have", "you", "been", "to", "N.Y.", "lately", "?")
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }
    }
    "not respect abbreviations" when {
      "the set of abbreviations is empty" in {
        implicit val abbrevs: Set[String] = Set.empty[String]
        val line = "Mr. Rippeth knows Dr. Xia."
        val expectedTokens = Seq("Mr", ".", "Rippeth", "knows", "Dr", ".", "Xia", ".")
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }
    }
    "handle numbers" when {

      implicit val emptySet: Set[String] = Set.empty[String]

      "they contain a comma" in {
        val line = "9,000"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they do not contain a comma" in {
        val line = "9000"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they contain a percentage" in {
        val line = "100%"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they contain a percentage and end with a decimal" in {
        val line = "10.0%."
        val expectedTokens = Seq("10.0%", ".")
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they contain a decimal" in {
        val line = "9000.0"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they contain a comma and a decimal" in {
        val line = "9,000.0"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they contain a negative" in {
        val line = "-9,000.0"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they contain a generation" in {
        val line = "1950s"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }
    }

    "handle quotes" in {
      val line =
        """
          |"Are you alright?", asked the woman.
        """.stripMargin
      implicit val emptySet: Set[String] = Set.empty[String]

      val expectedTokens = Seq("\"", "Are", "you", "alright", "?", "\"", ",", "asked", "the", "woman", ".")
      assertResult(expectedTokens.mkString(" "))(tokenize(line))
    }

    "handle double hyphens" in {
      val line = "Among 33 men, 28 have died -- more than the expected number."
      implicit val emptySet: Set[String] = Set.empty[String]

      val expectedTokens = Seq("Among", "33", "men", ",", "28", "have", "died", "--", "more", "than", "the", "expected", "number", ".")
      assertResult(expectedTokens.mkString(" "))(tokenize(line))
    }

    "handle apostrophes" when {
      "they are in plurals" in {
        val line = "Donohue's."
        implicit val emptySet: Set[String] = Set.empty[String]
        val expectedTokens = Seq("Donohue", "'s", ".")
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }
    }

    "handle paths" when {
      implicit val emptySet: Set[String] = Set.empty[String]
      "they are absolute unix paths" in {
        val line = "/absolute/path"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they are absolute Windows paths with single backslashes" in {
        val line = "C:\\absolute\\path"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }

      "they are absolute Windows paths with double backslashes" in {
        val line = "C:\\\\absolute\\path"
        val expectedTokens = Seq(line)
        assertResult(expectedTokens.mkString(" "))(tokenize(line))
      }
    }

    "handle emails" in {
      implicit val emptySet: Set[String] = Set.empty[String]
      val line = "test.email@gmail.com"
      val expectedTokens = Seq(line)
      assertResult(expectedTokens.mkString(" "))(tokenize(line))
    }
  }

}
