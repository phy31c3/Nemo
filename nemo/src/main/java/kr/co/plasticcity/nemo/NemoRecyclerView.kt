package kr.co.plasticcity.nemo

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kr.co.plasticcity.nemo.Parts.Group
import kotlin.reflect.KClass

class NemoRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RecyclerView(context, attrs, defStyleAttr)
{
	@DslMarker
	private annotation class Marker
	
	@Marker
	interface Define
	{
		var useSnap: Boolean
		
		fun <M, V : ViewBinding> group(model: Model.Singleton<M>, view: KClass<V>, tag: Any = Any(), block: SingleGroupDefine<M, V>.() -> Unit) = group(model, view, tag, block as GroupDefine<M, V>.() -> Unit)
		fun <M, V : ViewBinding> group(model: Model<M>, view: KClass<V>, tag: Any = Any(), block: GroupDefine<M, V>.() -> Unit)
		fun space(block: SpaceDefine.() -> Unit)
	}
	
	@Marker
	interface SingleGroupDefine<M, V : ViewBinding>
	{
		fun bind(block: Bind.(data: M, binding: V) -> Unit)
		fun divider(block: DividerDefine.() -> Unit)
	}
	
	@Marker
	interface GroupDefine<M, V : ViewBinding> : SingleGroupDefine<M, V>
	{
		var allowDragAndDrop: Boolean
		var allowSwipeToDismiss: Boolean
		
		fun placeHolder(num: Int = 1, block: Bind.(binding: V) -> Unit)
	}
	
	@Marker
	interface Bind
	{
		val groupPos: Int
		val globalPos: Int
	}
	
	@Marker
	interface DividerDefine
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
		{
			var value: M
		}
		
		interface List<M> : Model<M>, kotlin.collections.List<M>
		interface MutableList<M> : List<M>, kotlin.collections.MutableList<M>
	}
	
	companion object
	{
		fun <M> model(model: M, key: (M.() -> Any?)? = null): Model.Singleton<M>
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
	
	operator fun invoke(@RecyclerView.Orientation orientation: Int = VERTICAL, reverseLayout: Boolean = false, block: Define.() -> Unit): GroupArrange = Core().also { core ->
		object : Define
		{
			override var useSnap: Boolean
				get() = TODO("not implemented")
				set(value) = TODO("not implemented")
			
			@Suppress("UNCHECKED_CAST")
			override fun <M, V : ViewBinding> group(model: Model<M>, view: KClass<V>, tag: Any, block: GroupDefine<M, V>.() -> Unit) = object : GroupDefine<M, V>
			{
				var onBind: Bind.(data: M, binding: V) -> Unit = { _, _ -> }
				var onPlaceHolder: Bind.(binding: V) -> Unit = {}
				var numPlaceHolder = 0
				
				override var allowDragAndDrop: Boolean
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				
				override var allowSwipeToDismiss: Boolean
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				
				override fun bind(block: Bind.(data: M, binding: V) -> Unit)
				{
					onBind = block
				}
				
				override fun placeHolder(num: Int, block: Bind.(binding: V) -> Unit)
				{
					numPlaceHolder = num
					onPlaceHolder = block
				}
				
				override fun divider(block: DividerDefine.() -> Unit) = object : DividerDefine
				{
					override var sizeDp: Int
						get() = TODO("not implemented")
						set(value) = TODO("not implemented")
					override var color: String
						get() = TODO("not implemented")
						set(value) = TODO("not implemented")
					override var colorRes: Int
						get() = TODO("not implemented")
						set(value) = TODO("not implemented")
					override var drawableRes: Int
						get() = TODO("not implemented")
						set(value) = TODO("not implemented")
					override var show: DividerDefine.Position.() -> Int
						get() = TODO("not implemented")
						set(value) = TODO("not implemented")
				}.block()
			}.run {
				core.addParts(Group(
						tag = tag,
						model = model,
						view = view,
						onBind = onBind as Bind.(data: Any?, binding: ViewBinding) -> Unit,
						onPlaceHolder = onPlaceHolder as Bind.(binding: ViewBinding) -> Unit,
						numPlaceHolder = numPlaceHolder)
				)
				block()
			}
			
			override fun space(block: SpaceDefine.() -> Unit) = object : SpaceDefine
			{
				override var sizeDp: Int
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				override var color: String
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				override var colorRes: Int
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				override var fillViewport: Boolean
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				override var fillWeight: Float
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
			}.run {
				block()
			}
		}.apply {
			block()
			this@NemoRecyclerView.layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
			this@NemoRecyclerView.adapter = core.adapter
		}
	}
}

/*###################################################################################################################################
 * Core
 *###################################################################################################################################*/
private sealed class Parts(val tag: Any)
{
	class Group(tag: Any,
	            val model: NemoRecyclerView.Model<*>, view: KClass<out ViewBinding>,
	            val onBind: NemoRecyclerView.Bind.(data: Any?, binding: ViewBinding) -> Unit,
	            val onPlaceHolder: NemoRecyclerView.Bind.(binding: ViewBinding) -> Unit,
	            val numPlaceHolder: Int
	) : Parts(tag)
	
	class Space(tag: Any) : Parts(tag)
}

private class Core : NemoRecyclerView.GroupArrange
{
	val adapter = Adapter()
	val map = LinkedHashMap<Any, Parts>()
	
	fun addParts(parts: Parts) = map.put(parts.tag, parts)
	
	override fun bringForward(tag: Any)
	{
		TODO("not implemented")
	}
	
	override fun bringToFront(tag: Any)
	{
		TODO("not implemented")
	}
	
	override fun sendBackward(tag: Any)
	{
		TODO("not implemented")
	}
	
	override fun sendToBack(tag: Any)
	{
		TODO("not implemented")
	}
	
	override fun swap(tag1: Any, tag2: Any)
	{
		TODO("not implemented")
	}
	
	private inner class Adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()
	{
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
		{
			TODO("not implemented")
		}
		
		override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)
		{
			TODO("not implemented")
		}
		
		override fun getItemCount(): Int
		{
			TODO("not implemented")
		}
	}
}

/*###################################################################################################################################
 * Model
 *###################################################################################################################################*/
private class SingletonImpl<M> : NemoRecyclerView.Model.Singleton<M>
{
	override var value: M
		get() = TODO("not implemented")
		set(value) = TODO("not implemented")
}

private class MutableListImpl<M> : NemoRecyclerView.Model.MutableList<M>
{
	override val size: Int
		get() = TODO("not implemented")
	
	override fun contains(element: M): Boolean
	{
		TODO("not implemented")
	}
	
	override fun containsAll(elements: Collection<M>): Boolean
	{
		TODO("not implemented")
	}
	
	override fun get(index: Int): M
	{
		TODO("not implemented")
	}
	
	override fun indexOf(element: M): Int
	{
		TODO("not implemented")
	}
	
	override fun isEmpty(): Boolean
	{
		TODO("not implemented")
	}
	
	override fun iterator(): MutableIterator<M>
	{
		TODO("not implemented")
	}
	
	override fun lastIndexOf(element: M): Int
	{
		TODO("not implemented")
	}
	
	override fun add(element: M): Boolean
	{
		TODO("not implemented")
	}
	
	override fun add(index: Int, element: M)
	{
		TODO("not implemented")
	}
	
	override fun addAll(index: Int, elements: Collection<M>): Boolean
	{
		TODO("not implemented")
	}
	
	override fun addAll(elements: Collection<M>): Boolean
	{
		TODO("not implemented")
	}
	
	override fun clear()
	{
		TODO("not implemented")
	}
	
	override fun listIterator(): MutableListIterator<M>
	{
		TODO("not implemented")
	}
	
	override fun listIterator(index: Int): MutableListIterator<M>
	{
		TODO("not implemented")
	}
	
	override fun remove(element: M): Boolean
	{
		TODO("not implemented")
	}
	
	override fun removeAll(elements: Collection<M>): Boolean
	{
		TODO("not implemented")
	}
	
	override fun removeAt(index: Int): M
	{
		TODO("not implemented")
	}
	
	override fun retainAll(elements: Collection<M>): Boolean
	{
		TODO("not implemented")
	}
	
	override fun set(index: Int, element: M): M
	{
		TODO("not implemented")
	}
	
	override fun subList(fromIndex: Int, toIndex: Int): MutableList<M>
	{
		TODO("not implemented")
	}
}
