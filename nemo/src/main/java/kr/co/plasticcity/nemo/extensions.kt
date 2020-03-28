package kr.co.plasticcity.nemo

internal inline fun Boolean.alsoIf(expect: Boolean, block: () -> Unit): Boolean
{
	if (this == expect) block()
	return this
}

/**
 * @param from inclusive
 * @param to exclusive
 */
internal fun <E> MutableList<E>.removeRange(from: Int, to: Int)
{
	val filtered = filterIndexed { i, _ -> i < from || i >= to }
	clear()
	addAll(filtered)
}
