import java.io.*
import java.nio.charset.*
import java.util.*

fun main() = solve()

fun solve(
  inStream: InputStream = System.`in`,
  outStream: OutputStream = System.out,
  @Suppress("UNUSED_PARAMETER", "KotlinRedundantDiagnosticSuppress")
  logger: PrintStream = nullPrintStream(),
) {
  inStream.useWordSequence {
    outStream.usePrintStream {
      // TODO: parse input, solve the problem and print result here

      // Reading from input stream
      // then printing result to output stream
      println(nextLine.replace("input", "output"))

      /// Joining a collection and printing directly to PrintStream
      nextListOfInts.joinTo(this, ";")
      println()

      // PrintStream supports formatting output directly
      // Commonly use to format simple decimal number
      // Use DecimalFormatter for more powerful formatting
      nextDoubleOrNull?.let { printf("%.10f", it) }

      // Logging to logger stream separately
      logger.println("logging")
    }
  }
}

// ----------------------------------------------------------------- //

// region Competitive Programming Template

/*

# Input

* `bufferedReader(Charsets.ISO_8859_1)` is faster than `bufferedReader()` with default UTF-8
* `bufferedReader()` is faster than `readLine()`
* `readLine()` is faster than `Scanner`.
  Note: `readLine()` doesn't work well with older kotlin versions on some
  competitive programming platforms.
* `StringTokenizer`
  * Advantage: can change delimiters for the remaining of the string
  * Disadvantage: doesn't support regex as delimiters
* `splitToSequence`
  * Advantage: support regex as delimiters
  * Disadvantage: cannot change delimiters for the remaining of the string

# Input conversion

This template provides parsing input as tokens of to words and lines.

Additionally, convert words to common data types: Int, Long and Double.

For other types, like Byte/Short/Float/BigInt/BigDecimal,
use words or lines tokens and convert to the target types manually.

# Output

* Print directly to `PrintStream` is faster than having intermediate `String` or `StringBuilder`
* If printing directly to `PrintStream` is not easy,
  consider that `StringBuilder` is faster than concatenating `String`s

*/

// region InputStream/WordSequence

fun BufferedReader.wordSequence(): WordSequence = WordSequenceImpl(this)

fun <R> BufferedReader.useWordSequence(block: WordSequence.(BufferedReader) -> R) =
  use { wordSequence().block(this) }

fun <R> InputStream.useWordSequence(
  charset: Charset = Charsets.ISO_8859_1,
  block: WordSequence.(BufferedReader) -> R
) = bufferedReader(charset).useWordSequence(block)

interface WordSequence : Sequence<String> {
  val hasNextWord: Boolean
  val currentLineHasNextWord: Boolean
  val nextWord: String
  fun nextLine(trim: Boolean = true): String
}

/**
 * Provide operations to handle the input as a sequence of words.
 *
 * At the time calling any operation,
 * spaces from the current position to the first non-space character are completely ignored.
 *
 * Therefore, the first line of an operation is the line that contains the first next word.
 *
 * Line separators are used as markers to stop batch operations.
 *
 * Zero limit parameter in operations means unlimited.
 */
private class WordSequenceImpl(reader: BufferedReader) : WordSequence {
  private val lines =
    reader.lineSequence().filter { it.isNotBlank() }.iterator()
  private var currentLine = emptyTokenizer

  override fun iterator() = iterator

  private val iterator = object : Iterator<String> {
    override fun hasNext() = currentLineHasNextWord || lines.hasNext()

    override fun next(): String {
      if (!hasNext()) {
        throw NoSuchElementException()
      }

      if (!currentLineHasNextWord) {
        currentLine = StringTokenizer(lines.next())
      }

      return currentLine.nextToken()
    }
  }

  override val currentLineHasNextWord get() = currentLine.hasMoreTokens()
  override val hasNextWord get() = iterator.hasNext()
  override val nextWord get() = iterator.next()
  override fun nextLine(trim: Boolean): String {
    val line = if (currentLineHasNextWord)
      currentLine.nextToken("")
    else
      lines.next()
    return if (trim) line.trim() else line
  }

  companion object {
    val emptyTokenizer = StringTokenizer("")
  }
}

typealias OnEach<T> = (index: Int, value: T) -> Unit

// endregion

// region Next Line

inline val WordSequence.nextLine: String get() = nextLine()

inline val WordSequence.nextLineOrNull: String? get() = nextLineOrNull()

fun WordSequence.nextLineOrNull(trim: Boolean = true): String? =
  if (hasNextWord) nextLine(trim) else null

// endregion

// region Next Words

inline val WordSequence.nextWordOrNull: String? get() = if (hasNextWord) nextWord else null

inline val WordSequence.nextSequenceOfWords: Sequence<String> get() = nextSequenceOfWords()

fun WordSequence.nextSequenceOfWords(limit: Int = 0) = sequence {
  var wordCount = 0

  // read the first word to make sure starting on a valid line
  if (hasNextWord) {
    yield(nextWord)
    wordCount++
    if (limit in 1..wordCount) return@sequence
  }

  while (currentLineHasNextWord) {
    yield(nextWord)
    wordCount++
    if (limit in 1..wordCount) return@sequence
  }
}

inline val WordSequence.nextListOfWords: List<String> get() = nextListOfWords()

fun WordSequence.nextListOfWords(
  limit: Int = 0,
  onEach: OnEach<String>? = null
): List<String> = nextMutableListOfWords(limit, onEach)

inline val WordSequence.nextMutableListOfWords: MutableList<String> get() = nextMutableListOfWords()

fun WordSequence.nextMutableListOfWords(
  limit: Int = 0,
  onEach: OnEach<String>? = null
) = nextSequenceOfWords(limit)
  .onEachIndexed { index, value -> onEach?.invoke(index, value) }
  .toMutableList()

inline val WordSequence.nextSetOfWords: Set<String> get() = nextSetOfWords()

fun WordSequence.nextSetOfWords(
  limit: Int = 0,
  onEach: OnEach<String>? = null
): Set<String> = nextMutableSetOfWords(limit, onEach)

inline val WordSequence.nextMutableSetOfWords: MutableSet<String> get() = nextMutableSetOfWords()

fun WordSequence.nextMutableSetOfWords(
  limit: Int = 0,
  onEach: OnEach<String>? = null
) = nextSequenceOfWords(limit)
  .onEachIndexed { index, value -> onEach?.invoke(index, value) }
  .toMutableSet()

fun WordSequence.nextWordArray(
  size: Int,
  onEach: OnEach<String>? = null
): Array<String> {
  val seq = nextSequenceOfWords(size)
    .onEachIndexed { index, value -> onEach?.invoke(index, value) }
    .iterator()
  return Array(size) { seq.next() }
}

// endregion

// region Next Ints

inline val WordSequence.nextIntOrNull: Int? get() = nextIntOrNull()

fun WordSequence.nextIntOrNull(radix: Int = 10): Int? =
  nextWordOrNull?.toInt(radix)

inline val WordSequence.nextInt: Int get() = nextInt()

fun WordSequence.nextInt(radix: Int = 10): Int = nextWord.toInt(radix)

inline val WordSequence.nextSequenceOfInts: Sequence<Int> get() = nextSequenceOfInts()

fun WordSequence.nextSequenceOfInts(
  limit: Int = 0,
  radix: Int = 10,
) = nextSequenceOfWords(limit).map { it.toInt(radix) }

inline val WordSequence.nextListOfInts: List<Int> get() = nextListOfInts()

fun WordSequence.nextListOfInts(
  limit: Int = 0,
  radix: Int = 10,
  onEach: OnEach<Int>? = null
): List<Int> = nextMutableListOfInts(limit, radix, onEach)

inline val WordSequence.nextMutableListOfInts: MutableList<Int> get() = nextMutableListOfInts()

fun WordSequence.nextMutableListOfInts(
  limit: Int = 0,
  radix: Int = 10,
  onEach: OnEach<Int>? = null
) = nextSequenceOfInts(limit, radix)
  .onEachIndexed { index, value -> onEach?.invoke(index, value) }
  .toMutableList()

inline val WordSequence.nextSetOfInts: Set<Int> get() = nextSetOfInts()

fun WordSequence.nextSetOfInts(
  limit: Int = 0,
  radix: Int = 10,
  onEach: OnEach<Int>? = null
): Set<Int> = nextMutableSetOfInts(limit, radix, onEach)

inline val WordSequence.nextMutableSetOfInts: MutableSet<Int> get() = nextMutableSetOfInts()

fun WordSequence.nextMutableSetOfInts(
  limit: Int = 0,
  radix: Int = 10,
  onEach: OnEach<Int>? = null
) = nextSequenceOfInts(limit, radix)
  .onEachIndexed { index, value -> onEach?.invoke(index, value) }
  .toMutableSet()

fun WordSequence.nextIntArray(
  size: Int,
  radix: Int = 10,
  onEach: OnEach<Int>? = null
): IntArray {
  val seq = nextSequenceOfInts(size, radix)
    .onEachIndexed { index, value -> onEach?.invoke(index, value) }
    .iterator()
  return IntArray(size) { seq.next() }
}

// endregion

// region Next Longs

inline val WordSequence.nextLongOrNull: Long? get() = nextLongOrNull()

fun WordSequence.nextLongOrNull(radix: Int = 10): Long? =
  nextWordOrNull?.toLong(radix)

inline val WordSequence.nextLong: Long get() = nextLong()

fun WordSequence.nextLong(radix: Int = 10): Long = nextWord.toLong(radix)

inline val WordSequence.nextSequenceOfLongs: Sequence<Long> get() = nextSequenceOfLongs()

fun WordSequence.nextSequenceOfLongs(
  limit: Int = 0,
  radix: Int = 10,
) = nextSequenceOfWords(limit).map { it.toLong(radix) }

inline val WordSequence.nextListOfLongs: List<Long> get() = nextListOfLongs()

fun WordSequence.nextListOfLongs(
  limit: Int = 0,
  radix: Int = 10,
  onEach: OnEach<Long>? = null
): List<Long> = nextMutableListOfLongs(limit, radix, onEach)

inline val WordSequence.nextMutableListOfLongs: MutableList<Long> get() = nextMutableListOfLongs()

fun WordSequence.nextMutableListOfLongs(
  limit: Int = 0,
  radix: Int = 10,
  onEach: OnEach<Long>? = null
) = nextSequenceOfLongs(limit, radix)
  .onEachIndexed { index, value -> onEach?.invoke(index, value) }
  .toMutableList()

inline val WordSequence.nextSetOfLongs: Set<Long> get() = nextSetOfLongs()

fun WordSequence.nextSetOfLongs(
  limit: Int = 0,
  radix: Int = 10,
  onEach: OnEach<Long>? = null
): Set<Long> = nextMutableSetOfLongs(limit, radix, onEach)

inline val WordSequence.nextMutableSetOfLongs: MutableSet<Long> get() = nextMutableSetOfLongs()

fun WordSequence.nextMutableSetOfLongs(
  limit: Int = 0,
  radix: Int = 10,
  onEach: OnEach<Long>? = null
) = nextSequenceOfLongs(limit, radix)
  .onEachIndexed { index, value -> onEach?.invoke(index, value) }
  .toMutableSet()

fun WordSequence.nextLongArray(
  size: Int,
  radix: Int = 10,
  onEach: OnEach<Long>? = null
): LongArray {
  val seq = nextSequenceOfLongs(size, radix)
    .onEachIndexed { index, value -> onEach?.invoke(index, value) }
    .iterator()
  return LongArray(size) { seq.next() }
}

// endregion

// region Next Doubles

inline val WordSequence.nextDoubleOrNull: Double? get() = nextWordOrNull?.toDouble()

inline val WordSequence.nextDouble: Double get() = nextWord.toDouble()

inline val WordSequence.nextSequenceOfDoubles: Sequence<Double> get() = nextSequenceOfDoubles()

fun WordSequence.nextSequenceOfDoubles(
  limit: Int = 0,
) = nextSequenceOfWords(limit).map(String::toDouble)

inline val WordSequence.nextListOfDoubles: List<Double> get() = nextListOfDoubles()

fun WordSequence.nextListOfDoubles(
  limit: Int = 0,
  onEach: OnEach<Double>? = null
): List<Double> = nextMutableListOfDoubles(limit, onEach)

inline val WordSequence.nextMutableListOfDoubles: MutableList<Double> get() = nextMutableListOfDoubles()

fun WordSequence.nextMutableListOfDoubles(
  limit: Int = 0,
  onEach: OnEach<Double>? = null
) = nextSequenceOfDoubles(limit)
  .onEachIndexed { index, value -> onEach?.invoke(index, value) }
  .toMutableList()

fun WordSequence.nextDoubleArray(
  size: Int,
  onEach: OnEach<Double>? = null
): DoubleArray {
  val seq = nextSequenceOfDoubles(size)
    .onEachIndexed { index, value -> onEach?.invoke(index, value) }
    .iterator()
  return DoubleArray(size) { seq.next() }
}

// endregion

// region OutputStream/PrintStream

fun PrintStream.print(separator: String, vararg values: String?) =
  values.forEachIndexed { index, value ->
    print(if (index == 0) value else "$separator$value")
  }

fun PrintStream.println(separator: String, vararg values: String?) =
  print(separator, *values).also { println() }

fun OutputStream.toPrintStream(autoFlush: Boolean = true) =
  this as? PrintStream ?: PrintStream(this, autoFlush)

fun <R> OutputStream.usePrintStream(block: PrintStream.(OutputStream) -> R) =
  toPrintStream().use { it.block(this) }

fun nullPrintStream() = OutputStream.nullOutputStream().toPrintStream()

// endregion

// region Comparison

operator fun <T : Comparable<T>> T?.compareTo(other: T?) =
  compareValues(this, other)

fun <T : Comparable<T>> T?.nullsFirstCompareTo(other: T?) =
  compareValues(this, other)

infix fun <T : Comparable<T>> T?.nullsLastCompareTo(other: T?) = when {
  this === other -> 0
  this == null -> 1
  other == null -> -1
  else -> this.compareTo(other)
}

infix fun <T : Comparable<T>> T?.nflt(other: T?) =
  nullsFirstCompareTo(other) < 0

infix fun <T : Comparable<T>> T?.nfgt(other: T?) =
  nullsFirstCompareTo(other) > 0

infix fun <T : Comparable<T>> T?.nflte(other: T?) =
  nullsFirstCompareTo(other) <= 0

infix fun <T : Comparable<T>> T?.nfgte(other: T?) =
  nullsFirstCompareTo(other) >= 0

infix fun <T : Comparable<T>> T?.nllt(other: T?) = nullsLastCompareTo(other) < 0

infix fun <T : Comparable<T>> T?.nlgt(other: T?) = nullsLastCompareTo(other) > 0

infix fun <T : Comparable<T>> T?.nllte(other: T?) =
  nullsLastCompareTo(other) <= 0

infix fun <T : Comparable<T>> T?.nlgte(other: T?) =
  nullsLastCompareTo(other) >= 0

// endregion

// region Others

fun <T> IndexedValue<T>.toPair() = index to value

fun <T> Pair<Int, T>.toIndexedValue() = IndexedValue(first, second)

/**
 * You don't need this function if running on kotlin 1.4+
 *
 * See https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/remove-last.html
 */
// private fun <T> MutableList<T>.removeLast() =
//   if (isEmpty())
//     throw NoSuchElementException("List is empty.")
//   else removeAt(lastIndex)

/**
 * You don't need this function if running on kotlin 1.4+
 *
 * See https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/remove-last-or-null.html
 */
// fun <T> MutableList<T>.removeLastOrNull(): T? =
//   if (isEmpty()) null else removeAt(lastIndex)

// endregion

// endregion
