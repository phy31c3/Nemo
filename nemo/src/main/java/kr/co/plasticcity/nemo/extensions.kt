package kr.co.plasticcity.nemo

internal inline fun Boolean.alsoIf(expect: Boolean, block: () -> Unit): Boolean
{
	if (this == expect) block()
	return this
}
