package kr.co.plasticcity.nemo

import android.content.res.Resources

internal inline fun Boolean.alsoIf(expect: Boolean, block: () -> Unit): Boolean
{
	if (this == expect) block()
	return this
}

internal fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
internal fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
