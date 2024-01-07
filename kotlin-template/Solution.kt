@file:Suppress("unused", "UnusedReceiverParameter")

import java.io.*
import java.util.*

fun main() = solve()

fun solve() {
  with(output) {
    // TODO: parse input, solve the problem and print result here

    // Reading from input stream
    // then printing result to output stream
    println(nextLine.replace("input", "output"))

    // Reading an array and printing directly to PrintStream
    IntArray(3) { next() }.joinTo(this, ";")
    println()

    // Reading a collection with a known size
    List<Int>(3) { next() }.joinTo(this, ";")
    println()

    // Reading a collection with unknown size.
    // The first word could lie on another line
    // if the current line has only spaces unprocessed.
    // Terminating at the first EOL after the first word,
    // or when the `limit` is reached.
    nextSequence<Int>(2).toList().joinTo(this, ";")
    println()
    nextSequence<Int>(10).toList().joinTo(this, ";")
    println()

    // Same as previously, but without considering `limit`.
    // Terminating only at the first EOL after the first word.
    nextSequence<Int>().toList().joinTo(this, ";", postfix = "\n")

    // PrintStream supports formatting output directly
    // Commonly use to format simple decimal number
    // Use DecimalFormatter for more powerful formatting
    nextOrNull<Double>()?.let { printf("%.10f", it) }

    // Logging to logger stream separately
    logger?.println("logging")
  }
}

/******************************************************************************/

// region Competitive Programming Template

// region Input/Output

private var input = System.`in`
var output: PrintStream = System.out
  private set
var logger: PrintStream? = null
  private set
private val emptyTokenizer = StringTokenizer("")
private var currentLine = emptyTokenizer
private var lines = input.bufferedReader(Charsets.ISO_8859_1)
  .lineSequence().filter { it.isNotBlank() }.iterator()

fun reset(
  inStream: InputStream = System.`in`,
  outStream: OutputStream = System.out,
  loggerStream: PrintStream? = null,
) {
  input = inStream
  output = outStream as? PrintStream ?: PrintStream(outStream, true)
  logger = loggerStream

  currentLine = emptyTokenizer
  lines = input.bufferedReader(Charsets.ISO_8859_1)
    .lineSequence().filter { it.isNotBlank() }.iterator()
}

// endregion

// region Next Words

val currentLineHasNextWord get() = currentLine.hasMoreTokens()
val hasNextWord get() = currentLineHasNextWord || lines.hasNext()
val nextWord: String
  get() {
    if (!hasNextWord) throw NoSuchElementException()
    if (!currentLineHasNextWord) currentLine = StringTokenizer(lines.next())
    return currentLine.nextToken()
  }
inline val nextWordOrNull get() = if (hasNextWord) nextWord else null
fun nextSequenceOfWords(limit: Int = 0) = sequence {
  var wordCount = 0
  while ((wordCount == 0 && hasNextWord) || currentLineHasNextWord) {
    yield(nextWord)
    wordCount++
    if (limit in 1..wordCount) return@sequence
  }
}

// endregion

// region Next Line

inline val nextLine get() = nextLine()
fun nextLine(trim: Boolean = true): String {
  val line =
    if (currentLineHasNextWord) currentLine.nextToken("") else lines.next()
  return if (trim) line.trim() else line
}

inline val nextLineOrNull get() = nextLineOrNull()
fun nextLineOrNull(trim: Boolean = true) =
  if (hasNextWord) nextLine(trim) else null

// endregion

// region Next <T>s

inline fun <reified T> String.to() = when (val type = T::class) {
  String::class -> this
  Byte::class -> toByte()
  UByte::class -> toUByte()
  Short::class -> toShort()
  UShort::class -> toUShort()
  Int::class -> toInt()
  UInt::class -> toUInt()
  Long::class -> toLong()
  ULong::class -> toULong()
  Float::class -> toFloat()
  Double::class -> toDouble()
  else -> throw UnsupportedOperationException("$type is unsupported")
} as T

inline fun <reified T> nextOrNull() = if (hasNextWord) next<T>() else null
inline fun <reified T> next(): T = nextWord.to()
inline fun <reified T> nextSequence(limit: Int = 0) =
  nextSequenceOfWords(limit).map { it.to<T>() }

// endregion

// endregion
