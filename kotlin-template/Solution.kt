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

    // Reading a collection and printing directly to PrintStream
    nexts<List<Int>, _>().joinTo(this, ";")
    println()

    // Reading an array and printing directly to PrintStream
    nexts<IntArray>(3).joinTo(this, ";")
    println()

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

typealias OnEach<T> = (index: Int, value: T) -> Unit

inline fun <reified T : Collection<E>, reified E> nexts(
  limit: Int = 0,
  noinline onEach: OnEach<E>? = null
): T {
  val sequence = nextSequence<E>(limit).onEachIndexed { index, value ->
    onEach?.invoke(index, value)
  }
  return when (val typeT = T::class) {
    MutableList::class, List::class -> sequence.toMutableList()
    MutableSet::class, Set::class -> sequence.toMutableSet()

    else -> throw UnsupportedOperationException("$typeT is unsupported")
  } as T
}

inline fun <reified T> nexts(size: Int) = when (val type = T::class) {
  Array<String>::class -> with(nextSequence<String>(size).iterator()) {
    Array(size) { next() }
  }

  ByteArray::class -> with(nextSequence<Byte>(size).iterator()) {
    ByteArray(size) { next() }
  }

  Array<Byte>::class -> with(nextSequence<Byte>(size).iterator()) {
    Array(size) { next() }
  }

  ShortArray::class -> with(nextSequence<Short>(size).iterator()) {
    ShortArray(size) { next() }
  }

  Array<Short>::class -> with(nextSequence<Short>(size).iterator()) {
    Array(size) { next() }
  }

  IntArray::class -> with(nextSequence<Int>(size).iterator()) {
    IntArray(size) { next() }
  }

  Array<Int>::class -> with(nextSequence<Int>(size).iterator()) {
    Array(size) { next() }
  }

  LongArray::class -> with(nextSequence<Long>(size).iterator()) {
    LongArray(size) { next() }
  }

  Array<Long>::class -> with(nextSequence<Long>(size).iterator()) {
    Array(size) { next() }
  }

  FloatArray::class -> with(nextSequence<Float>(size).iterator()) {
    FloatArray(size) { next() }
  }

  Array<Float>::class -> with(nextSequence<Float>(size).iterator()) {
    Array(size) { next() }
  }

  DoubleArray::class -> with(nextSequence<Double>(size).iterator()) {
    DoubleArray(size) { next() }
  }

  Array<Double>::class -> with(nextSequence<Double>(size).iterator()) {
    Array(size) { next() }
  }

  else -> throw UnsupportedOperationException("$type is unsupported")
} as T

// endregion

// endregion
