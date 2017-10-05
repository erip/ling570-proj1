package edu.washington.rippeth.ling570.proj1

object EnglishTokenizer {

  private val abbrevs = "((\\w\\.)+)(\\W+)?".r
  private val contraction = "(\\w+)(n\\'t|'(?:d|re|s|m|ll|ve|s))(\\W+)?".r
  private val number = "((:?^|\\s)(?=.)([+-]?(?:0|(?:[1-9](?:\\d*|\\d{0,2}(?:,\\d{3})*)%?))(?:\\.\\d*[0-9]%?)?))(\\W+)?".r
  private val path = "((([a-zA-Z]:)?\\\\|/).+)".r
  private val email = "([a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+(?:[A-Z]{2}|com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum))(\\W+)?".r

  def processToken(tok: String)(implicit abbreviations: Set[String]): Seq[String] = {
    if(abbreviations.contains(tok)) Seq(tok)
    else yieldSubtokens(tok)
  }

  def yieldSubtokens(tok: String): Seq[String] = {
    if(tok.length == 1) tok :: Nil
    else tok match {
      case contraction(first, second, any @ _*) => {
        if(any.last == null) first::second::Nil
        else first::second::Nil ++ yieldSubtokens(any.last)
      }
      case abbrevs(first, any @ _*) => {
        if(any.last == null) first::Nil
        else first::Nil ++ yieldSubtokens(any.last)
      }
      case path(first, any @ _*) => {
        if(any.last == null || any.forall(first.contains)) first::Nil
        else first::Nil ++ yieldSubtokens(any.last)
      }
      case number(first, any @ _*) => {
        if(any.last == null) first::Nil
        else first::Nil ++ yieldSubtokens(any.last)
      }
      case email(first, any @ _*) => {
        if(any.last == null) first::Nil
        else first::Nil ++ yieldSubtokens(any.last)
      }
      case _ => tok.replaceAll("((-){2}|[,\\.!?:;\\-])", " $0 ")
                   .split("\\s+").filter(_.nonEmpty).toList match {
        case a::Nil => a::Nil
        case b => b.flatMap(yieldSubtokens)
      }
    }
  }

  def tokenize(text: String)(implicit abbreviations: Set[String]): String = {

    val splitText = text.replaceAll("""((-){2}|[!?;$"])""", " $0 ").split("\\s+")
    splitText.flatMap(processToken).mkString(" ").replaceAll("\\s+", " ").trim
  }
}
