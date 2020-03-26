@file:Suppress("NonAsciiCharacters", "PrivatePropertyName", "unused")

package kr.co.plasticcity.nemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kr.co.plasticcity.nemo.widget.Parts.Group
import kr.co.plasticcity.nemo.widget.Parts.Space
import java.util.*
import kotlin.collections.LinkedHashMap
import kotlin.collections.LinkedHashSet

class NemoRecyclerView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : RecyclerView(context, attrs, defStyleAttr)
{
	@DslMarker
	private annotation class Marker
	
	@Marker
	interface Define
	{
		var useSnap: Boolean
		
		fun <M, V : ViewBinding> group(
				model: Model.Singleton<M>,
				view: (LayoutInflater, ViewGroup, Boolean) -> V,
				tag: Any = Any(),
				block: SingleGroupDefine<M, V>.() -> Unit
		) = group(model, view, tag, block as GroupDefine<M, V>.() -> Unit)
		
		fun <M, V : ViewBinding> group(
				model: Model<M>,
				view: (LayoutInflater, ViewGroup, Boolean) -> V,
				tag: Any = Any(),
				block: GroupDefine<M, V>.() -> Unit)
		
		fun space(block: SpaceDefine.() -> Unit)
	}
	
	@Marker
	interface SingleGroupDefine<M, V : ViewBinding>
	{
		fun bind(block: ViewHolder.(data: M, binding: V) -> Unit)
		fun divider(block: DividerDefine.() -> Unit)
	}
	
	@Marker
	interface GroupDefine<M, V : ViewBinding> : SingleGroupDefine<M, V>
	{
		var allowDragAndDrop: Boolean
		var allowSwipeToDismiss: Boolean
		
		fun placeHolder(block: ViewHolder.(binding: V) -> Unit)
	}
	
	@Marker
	class ViewHolder(internal val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)
	{
		var modelPosition: Int = 0
			internal set
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
		fun <M> model(value: M, key: (M.() -> Any?)? = null): Model.Singleton<M>
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
	
	operator fun invoke(@RecyclerView.Orientation orientation: Int = VERTICAL, reverseLayout: Boolean = false, block: Define.() -> Unit): GroupArrange = Agent().also { agent ->
		object : Define
		{
			override var useSnap: Boolean
				get() = TODO("not implemented")
				set(value) = TODO("not implemented")
			
			@Suppress("UNCHECKED_CAST")
			override fun <M, V : ViewBinding> group(model: Model<M>, view: (LayoutInflater, ViewGroup, Boolean) -> V, tag: Any, block: GroupDefine<M, V>.() -> Unit) = object : GroupDefine<M, V>
			{
				var onBind: ViewHolder.(data: M, binding: V) -> Unit = { _, _ -> }
				var onPlaceHolder: (ViewHolder.(binding: V) -> Unit)? = null
				
				override var allowDragAndDrop: Boolean
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				
				override var allowSwipeToDismiss: Boolean
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				
				override fun bind(block: ViewHolder.(data: M, binding: V) -> Unit)
				{
					onBind = block
				}
				
				override fun placeHolder(block: ViewHolder.(binding: V) -> Unit)
				{
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
				agent.addGroup(
						tag = tag,
						model = model as ModelInternal,
						viewProvider = view,
						onBind = onBind as ViewHolder.(Any?, ViewBinding) -> Unit,
						onPlaceHolder = onPlaceHolder as (ViewHolder.(ViewBinding) -> Unit)?)
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
			this@NemoRecyclerView.adapter = agent.adapter
		}
	}
}

/*###################################################################################################################################
 * Core
 *###################################################################################################################################*/
private typealias ViewProvider = (LayoutInflater, ViewGroup, Boolean) -> ViewBinding

private sealed class Parts(val tag: Any)
{
	abstract val size: Int
	
	var position = 0
	
	class Group(tag: Any,
	            val model: ModelInternal,
	            val viewProvider: ViewProvider,
	            val onBind: NemoRecyclerView.ViewHolder.(data: Any?, binding: ViewBinding) -> Unit,
	            val onPlaceHolder: (NemoRecyclerView.ViewHolder.(binding: ViewBinding) -> Unit)? = null
	) : Parts(tag)
	{
		override val size: Int
			get() = if (model.isNotEmpty) model.size else if (onPlaceHolder != null) 1 else 0
	}
	
	class Space(tag: Any) : Parts(tag)
	{
		override val size: Int = 1
	}
}

private class Agent : NemoRecyclerView.GroupArrange
{
	val adapter = Adapter()
	
	private val parts = LinkedHashMap<Any, Parts>()
	private val tagViewType = LinkedHashSet<Any>()
	private val tagPosition = TreeMap<Int, Any>()
	
	private var count = 0
	
	fun addGroup(tag: Any,
	             model: ModelInternal,
	             viewProvider: ViewProvider,
	             onBind: NemoRecyclerView.ViewHolder.(data: Any?, binding: ViewBinding) -> Unit,
	             onPlaceHolder: (NemoRecyclerView.ViewHolder.(binding: ViewBinding) -> Unit)?)
	{
		val group = Group(tag, model, viewProvider, onBind, onPlaceHolder)
		parts[tag] = group
		tagViewType += tag
		tagPosition[count] = tag
		group.position = count
		count += group.size
	}
	
	private fun tagOfViewType(viewType: Int) = tagViewType.elementAt(viewType)
	private fun tagOfPosition(position: Int) = tagPosition.floorEntry(position).value
	private fun viewTypeOfTag(tag: Any) = tagViewType.indexOf(tag)
	private fun viewTypeOfPosition(position: Int) = viewTypeOfTag(tagOfPosition(position))
	
	private inner class Adapter : RecyclerView.Adapter<NemoRecyclerView.ViewHolder>()
	{
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NemoRecyclerView.ViewHolder
		{
			return parts[tagOfViewType(viewType)].let { parts ->
				when (parts)
				{
					is Group -> parts.viewProvider(LayoutInflater.from(parent.context), parent, false)
					is Space -> TODO("not implemented")
					else -> TODO("null 처리")
				}
			}.let { binding ->
				NemoRecyclerView.ViewHolder(binding)
			}
		}
		
		override fun onBindViewHolder(holder: NemoRecyclerView.ViewHolder, position: Int)
		{
			parts[tagOfPosition(position)].also { parts ->
				when (parts)
				{
					is Group ->
					{
						if (parts.model.isNotEmpty)
						{
							holder.modelPosition = position - parts.position
							parts.onBind(holder, parts.model[holder.modelPosition], holder.binding)
						}
						else if (parts.onPlaceHolder != null)
						{
							parts.onPlaceHolder.invoke(holder, holder.binding)
						}
					}
					is Space -> TODO("not implemented")
				}
			}
		}
		
		override fun getItemCount(): Int = count
		override fun getItemViewType(position: Int) = viewTypeOfPosition(position)
	}
	
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
}

/*###################################################################################################################################
 * Model
 *###################################################################################################################################*/
private interface ModelInternal
{
	val size: Int
	
	operator fun get(index: Int): Any?
}

private val ModelInternal.isEmpty: Boolean
	get() = size == 0

private val ModelInternal.isNotEmpty: Boolean
	get() = size > 0

private class SingletonImpl<M>(value: M) : ModelInternal, NemoRecyclerView.Model.Singleton<M>
{
	override val size: Int = 1
	
	override var value: M
		get() = TODO("not implemented")
		set(value) = TODO("not implemented")
	
	override fun get(index: Int): Any? = value
}

private class MutableListImpl<M>(list: MutableList<M>) : ModelInternal, NemoRecyclerView.Model.MutableList<M>
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
