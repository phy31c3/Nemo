package kr.co.plasticcity.nemo

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class NemoRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RecyclerView(context, attrs, defStyleAttr)
{
	operator fun invoke(@RecyclerView.Orientation orientation: Int = VERTICAL, reverseLayout: Boolean = false, block: Define.() -> Unit): GroupOrder
	{
		TODO()
	}
	
	@DslMarker
	internal annotation class Marker
	
	@Marker
	interface Define
	{
		var useSnap: Boolean
		
		fun <M, V : ViewBinding> group(model: Model<M>, view: V, tag: Any? = null, block: GroupDefine.() -> Unit)
		fun space(block: SpaceDefine.() -> Unit)
	}
	
	@Marker
	interface GroupDefine
	{
	
	}
	
	@Marker
	interface SpaceDefine
	{
	
	}
	
	interface Model<M>
	{
		interface Singleton<M> : Model<M>
		interface List<M> : Model<M>
		interface MutableList<M> : Model<M>
	}
	
	interface GroupOrder
}
