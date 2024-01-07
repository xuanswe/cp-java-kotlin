# Input

* `bufferedReader(Charsets.ISO_8859_1)` is faster than `bufferedReader()` with default UTF-8
* `bufferedReader()` is faster than `readLine()`
* `readLine()` is faster than `Scanner`.
  Note: `readLine()` doesn't work well with older kotlin versions on some
  competitive programming platforms.
* `System.in` and `System.out` are opened by JVM,
  so they should be flushed and closed by JVM.
* `StringTokenizer`
  * Advantage: can change delimiters for the remaining of the string
  * Disadvantage: doesn't support regex as delimiters
* `splitToSequence`
  * Advantage: support regex as delimiters
  * Disadvantage: cannot change delimiters for the remaining of the string

# Input conversion

This template provides parsing input as tokens to words and lines.

Additionally, convert words to native signed and unsigned numbers.

For other complex types, like BigInt/BigDecimal,
use words or lines tokens and convert as you will.

# Output

* Print directly to `PrintStream` is faster than having intermediate `String` or `StringBuilder`
* If printing directly to `PrintStream` is not easy,
  consider that `StringBuilder` is faster than concatenating `String`s
