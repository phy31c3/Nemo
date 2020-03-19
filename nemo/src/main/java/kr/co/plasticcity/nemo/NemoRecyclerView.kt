package kr.co.plasticcity.nemo

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

class NemoRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RecyclerView(context, attrs, defStyleAttr)
{
	@DslMarker
	internal annotation class Marker
	
	companion object
	{
		fun <M> model(model: M, key: (M.() -> Any?)? = null): Model.Singleton<M?>
		{
			TODO()
		}
		
		fun <M> model(list: List<M>, key: (M.() -> Any?)? = null): Model.List<M>
		{
			TODO()
		}
		
		fun <M> model(list: MutableList<M>, key: (M.() -> Any?)? = null): Model.MutableList<M>
		{
			TODO()
		}
	}
	
	operator fun invoke(@RecyclerView.Orientation orientation: Int = VERTICAL, reverseLayout: Boolean = false, block: Define.() -> Unit): GroupArrange
	{
		TODO()
	}
	
	@Marker
	interface Define
	{
		var useSnap: Boolean
		
		fun <M, V : ViewBinding> group(model: Model<M>, view: KClass<V>, tag: Any? = null, block: GroupDefine<M, V>.() -> Unit)
		fun space(block: SpaceDefine.() -> Unit)
	}
	
	@Marker
	interface GroupDefine<M, V : ViewBinding>
	{
		var allowDragAndDrop: Boolean
		var allowSwipeToDismiss: Boolean
		
		fun bind(block: Bind.(data: M, binding: V) -> Unit)
		fun placeHolder(num: Int = 1, block: Bind.(binding: V) -> Unit)
		fun divider(block: Divider.() -> Unit)
	}
	
	@Marker
	interface Bind
	{
		val groupPos: Int
		val globalPos: Int
	}
	
	@Marker
	interface Divider
	{
		var sizeDp: Int
		var color: String
		var colorRes: Int
		var drawableRes: Int
		var show: Position.() -> Int
		
		@Marker
		object Position
		{
			const val BEGINNING = 0x7FFFFFFE
			const val MIDDLE = 0x7FFFFFFD
			const val END = 0x7FFFFFFB
		}
	}
	
	@Marker
	interface SpaceDefine
	{
		var sizeDp: Int
		var color: String
		var colorRes: Int
		var fillViewport: Boolean
		var fillWeight: Float
	}
	
	interface GroupArrange
	{
		fun bringForward(tag: Any)
		fun bringToFront(tag: Any)
		fun sendBackward(tag: Any)
		fun sendToBack(tag: Any)
		fun swap(tag1: Any, tag2: Any)
	}
	
	interface Model<M>
	{
		interface Singleton<M> : Model<M>
		interface List<M> : Model<M>, kotlin.collections.List<M>
		interface MutableList<M> : Model<M>, kotlin.collections.MutableList<M>
	}
}
