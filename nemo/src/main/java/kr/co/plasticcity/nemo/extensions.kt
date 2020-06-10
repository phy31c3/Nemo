package kr.co.plasticcity.nemo

import android.content.res.Resources

internal inline fun Boolean.alsoIf(expect: Boolean, block: () -> Unit): Boolean
{
	if (this == expect) block()
	return this
}

internal fun Int.toDp(): Float = (this / Resources.getSystem().displayMetrics.density)
internal fun Float.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
