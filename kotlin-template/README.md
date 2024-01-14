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

Solutions to output multiple strings.
Listed from the best performance on top to the least performance.

* Print directly to `PrintStream`
* Appending to a `StringBuilder`
* Concatenating `String`s

# Compatibility

* Some platforms, like beecrowd/urionlinejudge, require to keep `args` in the `main` method.

```kotlin
@Suppress("unused_parameter")
fun main(args: Array<String>) {
}
```

* [Trailing comma](https://kotlinlang.org/docs/whatsnew14.html#trailing-comma)
  is supported only since Kotlin 1.4
