@file:Suppress("unused")

import java.io.*
import java.util.*

@Suppress("unused_parameter")
fun main(args: Array<String>) = solve()

fun solve() {
  with(output) {
    // TODO: parse input, solve the problem and print result here

    // Reading the next line and printing directly to PrintStream
    println(nextLine.replace("input", "output"))

    // Reading an array and printing directly to PrintStream
    IntArray(3) { next() }.joinTo(this)
    println()

    // Reading a collection with a known size
    List<Int>(3) { next() }.joinTo(this, ";")
    println()

    // Read the next line, split into a sequence of words,
    // and convert to a specific data type.
    // Then convert the sequence to a collection.
    nexts<Int>().toList().joinTo(this, ";", postfix = "\n")

    // PrintStream supports formatting output directly
    // Commonly use to format simple decimal number
    // Use DecimalFormatter for more powerful formatting
    nextOrNull<Double>()?.let { printf("%.10f", it) }

    // Logging to logger stream separately
    logger?.println("logging")
  }
}

/********************** Competitive Programming Template **********************/

// region Input/Output

private var input = System.`in`
var output: PrintStream = System.out
  private set
var logger: PrintStream? = null
  private set
private var currentLine = StringTokenizer("")
private var lines = lines()
private fun lines() = input.bufferedReader(Charsets.ISO_8859_1)
  .lineSequence().filter { it.isNotBlank() }.iterator()

fun reset(
  inStream: InputStream = System.`in`,
  outStream: OutputStream = System.out,
  loggerStream: PrintStream? = null
) {
  input = inStream
  output = outStream as? PrintStream ?: PrintStream(outStream, true)
  logger = loggerStream

  currentLine = StringTokenizer("")
  lines = lines()
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
inline fun <reified T> nexts() = sequence<T> {
  if (hasNextWord) yield(nextWord.to())
  while (currentLineHasNextWord) yield(nextWord.to())
}

// endregion

// region For Kotlin before 1.4

private fun <T> MutableList<T>.removeLast() =
  if (isEmpty()) throw NoSuchElementException("List is empty.")
  else removeAt(lastIndex)

private fun <T> MutableList<T>.removeLastOrNull(): T? =
  if (isEmpty()) null else removeAt(lastIndex)

// endregion
