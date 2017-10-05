# Rule-based English Tokenization

## Task

Generate an English tokenizer which handles several exceptional token classes including:

- email
- paths
- urls
- abbreviations
- numbers
- contractions

## Results

As a heuristic approach of counting tokens naively, I split lines on whitespace in the source text, `ex2`, and found that there are 39824 tokens. I found, however, that after applying my tokenization algorithm to the same source, there were 47641 tokens -- nearly 20% more tokens found. This was rather eye-opening.

Similarly, I counted the number of unique tokens in the output. Surprisingly, before tokenization, there were 10425 unique tokens; however, after applying my tokenization algorithm, there were fewer unique tokens: only 7765.

This decrease in unique tokens seems to come from prefixed and suffixed punctuation, counting existing tokens multiple times.

## Approach

I process the text line-by-line. For each line, I initially replace all punctuation with
exception of periods, commas, and slashes with that punctuation padded with whitespace. I then split on whitespace to produce N chunks, where each chunk is _at least_ one token.

For each chunk, I then attempt to parse all tokens from that chunk, checking the exception token classes first with regexes. I extract the tokens from the line and process the remaining sub-line if any exists.

## Tools

Aside from a unit testing framework for Scala, there were no external tools used for this project.

## Testing

Unit tests were developed to test exception token classes.

## To do

Write more tests to cover more edge cases.
