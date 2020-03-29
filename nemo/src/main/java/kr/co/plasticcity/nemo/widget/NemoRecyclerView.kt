@file:Suppress("NonAsciiCharacters", "PrivatePropertyName", "unused")

package kr.co.plasticcity.nemo.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.collection.ArraySet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kr.co.plasticcity.nemo.alsoIf
import kr.co.plasticcity.nemo.removeRange
import kr.co.plasticcity.nemo.widget.Layer.Group
import kr.co.plasticcity.nemo.widget.Layer.Space
import java.util.*

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
		
		fun <PH : ViewBinding> placeholder(view: (LayoutInflater, ViewGroup, Boolean) -> PH, block: (ViewHolder.(binding: PH) -> Unit)? = null)
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
			const val BEGINNING = -0x00000002
			const val MIDDLE = -0x00000003
			const val END = -0x00000005
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
		{
			fun update(elements: Collection<M>)
		}
	}
	
	companion object
	{
		fun <M> model(value: M, key: M.() -> Any? = { null }): Model.Singleton<M> = SingletonImpl(value, key)
		fun <M> model(list: List<M>, key: M.() -> Any? = { null }): Model.List<M> = ListImpl(list, key)
		fun <M> model(list: MutableList<M>, key: M.() -> Any? = { null }): Model.MutableList<M> = MutableListImpl(list, key)
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
				var onBind: ViewHolder.(Any?, ViewBinding) -> Unit = { _, _ -> }
				var placeholderProvider: ViewProvider? = null
				var onPlaceHolder: (ViewHolder.(ViewBinding) -> Unit)? = null
				
				override var allowDragAndDrop: Boolean
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				
				override var allowSwipeToDismiss: Boolean
					get() = TODO("not implemented")
					set(value) = TODO("not implemented")
				
				override fun bind(block: ViewHolder.(data: M, binding: V) -> Unit)
				{
					onBind = block as ViewHolder.(Any?, ViewBinding) -> Unit
				}
				
				override fun <PH : ViewBinding> placeholder(view: (LayoutInflater, ViewGroup, Boolean) -> PH, block: (ViewHolder.(binding: PH) -> Unit)?)
				{
					placeholderProvider = view
					onPlaceHolder = block as? ViewHolder.(ViewBinding) -> Unit
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
				agent.add(Group(
						tag = tag,
						model = model as ModelInternal,
						viewProvider = view,
						onBind = onBind,
						placeholderProvider = placeholderProvider,
						onPlaceHolder = onPlaceHolder)
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
			this@NemoRecyclerView.adapter = agent
		}
	}
}

/*###################################################################################################################################
 * Core
 *###################################################################################################################################*/
private typealias ViewProvider = (LayoutInflater, ViewGroup, Boolean) -> ViewBinding

private sealed class Layer(val tag: Any)
{
	abstract val size: Int
	
	class Group(tag: Any,
	            val model: ModelInternal,
	            val viewProvider: ViewProvider,
	            val onBind: NemoRecyclerView.ViewHolder.(data: Any?, binding: ViewBinding) -> Unit,
	            val placeholderProvider: ViewProvider?,
	            val onPlaceHolder: (NemoRecyclerView.ViewHolder.(binding: ViewBinding) -> Unit)?
	) : Layer(tag)
	{
		override val size: Int
			get() = if (model.isNotEmpty) model.size else if (placeholderProvider != null) 1 else 0
	}
	
	class Space(tag: Any) : Layer(tag)
	{
		override val size: Int = 1
	}
}

private class Agent : RecyclerView.Adapter<NemoRecyclerView.ViewHolder>(), NemoRecyclerView.GroupArrange
{
	companion object
	{
		var itemIdPool = 0
	}
	
	private val layers = mutableMapOf<Any, Layer>()
	
	/* for layer order */
	private val layerOrder = LinkedList<Layer>()
	private val layerPosition = TreeMap<Int, Layer>()
	
	/* for immutable view type or item id */
	private val layerArrayForViewType = ArraySet<Layer>()
	private val keyMapForItemId = mutableMapOf<Any, Int>()
	
	private var count = 0
	
	init
	{
		setHasStableIds(true)
		registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver()
		{
			override fun onChanged() = reorder()
			override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = reorder()
			override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = reorder()
		})
	}
	
	fun add(group: Group)
	{
		layers[group.tag] = group
		layerOrder += group
		layerPosition[count] = group
		layerArrayForViewType += group
		group.model.agent = this
		group.model.adapterPosition = count
		count += group.size
	}
	
	private fun reorder()
	{
		count = 0
		layerPosition.clear()
		layerOrder.forEach { layer ->
			if (layer is Group)
			{
				layer.model.adapterPosition = count
			}
			layerPosition[count] = layer
			count += layer.size
		}
	}
	
	private fun layerAtPosition(position: Int) = layerPosition.floorEntry(position).value
	private fun layerOfViewType(viewType: Int) = layerArrayForViewType.elementAt(viewType.toNormalViewType())
	
	private fun Int.toNormalViewType(): Int = this and -0x40000001
	private fun Int.toPlaceholderViewType(): Int = this or 0x40000000
	private fun Int.isNormalViewType(): Boolean = this and 0x40000000 == 0
	private fun Int.isPlaceholderViewType(): Boolean = this and 0x40000000 != 0
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NemoRecyclerView.ViewHolder
	{
		return layerOfViewType(viewType).let { layer ->
			when (layer)
			{
				is Group ->
				{
					when
					{
						viewType.isNormalViewType() ->
						{
							layer.viewProvider(LayoutInflater.from(parent.context), parent, false)
						}
						layer.placeholderProvider != null ->
						{
							layer.placeholderProvider.invoke(LayoutInflater.from(parent.context), parent, false)
						}
						else ->
						{
							TODO("예외 던지기")
						}
					}
				}
				is Space -> TODO("not implemented")
			}
		}.let { binding ->
			NemoRecyclerView.ViewHolder(binding)
		}
	}
	
	override fun onBindViewHolder(holder: NemoRecyclerView.ViewHolder, position: Int)
	{
		layerAtPosition(position).also { layer ->
			when (layer)
			{
				is Group ->
				{
					if (layer.model.isNotEmpty)
					{
						holder.modelPosition = position - layer.model.adapterPosition
						layer.onBind(holder, layer.model[holder.modelPosition], holder.binding)
					}
					else if (layer.onPlaceHolder != null)
					{
						layer.onPlaceHolder.invoke(holder, holder.binding)
					}
				}
				is Space -> TODO("not implemented")
			}
		}
	}
	
	override fun getItemCount(): Int = count
	override fun getItemViewType(position: Int): Int = layerAtPosition(position).let { layer ->
		when (layer)
		{
			is Group ->
			{
				if (layer.model.isNotEmpty) layerArrayForViewType.indexOf(layer)
				else /* placeholder */ layerArrayForViewType.indexOf(layer).toPlaceholderViewType()
			}
			is Space -> layerArrayForViewType.indexOf(layer)
		}
	}
	
	override fun getItemId(position: Int): Long = layerAtPosition(position).let { layer ->
		data class Pair(val key: Any?, val viewType: Int)
		when (layer)
		{
			is Group ->
			{
				if (layer.model.isNotEmpty)
				{
					val modelPosition = position - layer.model.adapterPosition
					Pair(layer.model.keyAt(modelPosition), layerArrayForViewType.indexOf(layer))
				}
				else /* placeholder */
				{
					Pair("placeholder", layerArrayForViewType.indexOf(layer).toPlaceholderViewType())
				}
			}
			is Space ->
			{
				Pair("space", layerArrayForViewType.indexOf(layer))
			}
		}.let { (key, viewType) ->
			if (key != null)
			{
				(keyMapForItemId[key] ?: itemIdPool++.also { itemId ->
					keyMapForItemId[key] = itemId
				}).let { itemId ->
					(viewType.toLong() shl Int.SIZE_BITS) or itemId.toLong()
				}
			}
			else
			{
				RecyclerView.NO_ID
			}
		}
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
	var agent: Agent?
	var adapterPosition: Int
	
	operator fun get(index: Int): Any?
	fun keyAt(index: Int): Any?
}

private val ModelInternal.isEmpty: Boolean
	get() = size == 0

private val ModelInternal.isNotEmpty: Boolean
	get() = size > 0

private class SingletonImpl<M>(value: M, private val key: M.() -> Any?) : ModelInternal, NemoRecyclerView.Model.Singleton<M>
{
	override val size: Int = 1
	override var agent: Agent? = null
	override var adapterPosition: Int = 0
	override var value: M = value
		set(value)
		{
			if (field != value)
			{
				field = value
				agent?.notifyItemChanged(adapterPosition)
			}
		}
	
	override fun get(index: Int): Any? = value
	override fun keyAt(index: Int): Any? = value.key()
}

private class ListImpl<M>(val list: List<M>, val key: M.() -> Any?) : ModelInternal, NemoRecyclerView.Model.List<M>, List<M> by list
{
	override var agent: Agent? = null
	override var adapterPosition: Int = 0
	
	override fun keyAt(index: Int): Any? = this[index].key()
}

private class MutableListImpl<M>(val list: MutableList<M>, val key: M.() -> Any?) : ModelInternal, NemoRecyclerView.Model.MutableList<M>, MutableList<M> by list
{
	override var agent: Agent? = null
	override var adapterPosition: Int = 0
	
	override fun keyAt(index: Int): Any? = this[index].key()
	
	override fun add(element: M): Boolean
	{
		val index = size
		return list.add(element).alsoIf(true) {
			agent?.notifyItemInserted(adapterPosition + index)
		}
	}
	
	override fun add(index: Int, element: M)
	{
		list.add(index, element)
		agent?.notifyItemInserted(adapterPosition + index)
	}
	
	override fun addAll(index: Int, elements: Collection<M>): Boolean
	{
		return list.addAll(index, elements).alsoIf(true) {
			agent?.notifyItemRangeInserted(adapterPosition + index, elements.size)
		}
	}
	
	override fun addAll(elements: Collection<M>): Boolean
	{
		val index = size
		return list.addAll(elements).alsoIf(true) {
			agent?.notifyItemRangeInserted(adapterPosition + index, elements.size)
		}
	}
	
	override fun clear()
	{
		val count = size
		list.clear()
		agent?.notifyItemRangeRemoved(adapterPosition, count)
	}
	
	override fun remove(element: M): Boolean
	{
		val index = list.indexOf(element)
		return list.remove(element).alsoIf(true) {
			agent?.notifyItemRemoved(adapterPosition + index)
		}
	}
	
	override fun removeAll(elements: Collection<M>): Boolean
	{
		val ranges = mutableListOf<Range>()
		var current = Range(-1)
		var removed = 0
		list.forEachIndexed { index, m ->
			if (elements.contains(m))
			{
				when
				{
					current.index == -1 ->
					{
						current = Range(index)
						ranges += current
					}
					current.next == index -> ++current
					else ->
					{
						removed += current.count
						current = Range(index, -removed)
						ranges += current
					}
				}
			}
		}
		return list.removeAll(elements).alsoIf(true) {
			ranges.forEach { range ->
				agent?.notifyItemRangeRemoved(range.position, range.count)
			}
		}
	}
	
	override fun removeAt(index: Int): M
	{
		return list.removeAt(index).also {
			agent?.notifyItemRemoved(adapterPosition + index)
		}
	}
	
	override fun retainAll(elements: Collection<M>): Boolean
	{
		val ranges = mutableListOf<Range>()
		var current = Range(-1)
		var removed = 0
		list.forEachIndexed { index, m ->
			if (!elements.contains(m))
			{
				when
				{
					current.index == -1 ->
					{
						current = Range(index)
						ranges += current
					}
					current.next == index -> ++current
					else ->
					{
						removed += current.count
						current = Range(index, -removed)
						ranges += current
					}
				}
			}
		}
		return list.retainAll(elements).alsoIf(true) {
			ranges.forEach { range ->
				agent?.notifyItemRangeRemoved(range.position, range.count)
			}
		}
	}
	
	override fun set(index: Int, element: M): M
	{
		return list.set(index, element).also { prevElement ->
			if (prevElement != element)
			{
				agent?.notifyItemChanged(adapterPosition + index)
			}
		}
	}
	
	override fun update(elements: Collection<M>)
	{
		fun List<M>.findSameKey(from: Int, key: Any?): Int
		{
			if (from > lastIndex) return -1
			
			for (i in from..lastIndex)
			{
				if (this[i].key() == key)
				{
					return i
				}
			}
			
			return -1
		}
		
		fun MutableList<Range>.submit()
		{
			if (isNotEmpty())
			{
				forEach { range ->
					agent?.notifyItemRangeChanged(range.position, range.count)
				}
			}
			clear()
		}
		
		fun MutableList<Range>.update(lIndex: Int, rElement: M)
		{
			val lElement = list[lIndex]
			if (lElement != rElement)
			{
				list[lIndex] = rElement
				if (isEmpty() || last().next != lIndex)
				{
					this += Range(lIndex)
				}
				else
				{
					last().inc()
				}
			}
		}
		
		var lIndex = 0
		val change = mutableListOf<Range>()
		val insert = mutableListOf<M>()
		elements.forEach { rElement ->
			var lMark = list.findSameKey(lIndex, rElement.key())
			when (lMark)
			{
				-1 /* no same key */ ->
				{
					change.submit()
					insert += rElement
				}
				lIndex /* same with current index */ ->
				{
					if (insert.isNotEmpty())
					{
						addAll(lIndex, insert)
						lIndex += insert.size
						insert.clear()
					}
					change.update(lIndex, rElement)
					++lIndex
				}
				else /* lMark > lIndex */ ->
				{
					change.submit()
					if (insert.isNotEmpty())
					{
						addAll(lIndex, insert)
						lIndex += insert.size
						lMark += insert.size
						insert.clear()
					}
					list.removeRange(lIndex, lMark)
					agent?.notifyItemRangeRemoved(adapterPosition + lIndex, lMark - lIndex)
					change.update(lIndex, rElement)
					++lIndex
				}
			}
		}
		change.submit()
		if (insert.isNotEmpty())
		{
			addAll(lIndex, insert)
			lIndex += insert.size
		}
		if (lIndex < list.size)
		{
			val count = list.size - lIndex
			list.removeRange(lIndex, list.size)
			agent?.notifyItemRangeRemoved(adapterPosition + lIndex, count)
		}
	}
	
	private inner class Range(val index: Int, val padding: Int = 0)
	{
		val position: Int
			get() = adapterPosition + index + padding
		
		val next: Int
			get() = index + count
		
		var count = 1
			private set
		
		operator fun inc(): Range
		{
			++count
			return this
		}
	}
}
