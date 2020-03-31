@file:Suppress("NonAsciiCharacters", "PrivatePropertyName", "unused")

package kr.co.plasticcity.nemo.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.ArraySet
import androidx.core.view.children
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kr.co.plasticcity.nemo.alsoIf
import kr.co.plasticcity.nemo.toPx
import kr.co.plasticcity.nemo.widget.Layer.Group
import kr.co.plasticcity.nemo.widget.Layer.Space
import java.util.*
import kotlin.math.roundToInt

class NemoRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr)
{
	private val spaceDecoration: SpaceDecoration = SpaceDecoration()
	private val dividerDecoration: DividerDecoration = DividerDecoration()
	
	init
	{
		addItemDecoration(spaceDecoration)
		addItemDecoration(dividerDecoration)
	}
	
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
		
		/**
		 * ex) "#FFFFFFFF"
		 */
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
			const val INCLUDE_PLACEHOLDER = -0x00000009
			const val KEEP_ALPHA = -0x00000011
			const val KEEP_POSITION = -0x00000021
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
	
	@Suppress("RemoveRedundantQualifierName")
	operator fun invoke(@RecyclerView.Orientation orientation: Int = VERTICAL,
	                    reverseLayout: Boolean = false,
	                    block: Define.() -> Unit
	): GroupArrange = kr.co.plasticcity.nemo.widget.Adapter().also { adapter ->
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
				var divider: Group.Divider? = null
				
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
					override var sizeDp: Int = 0
					override var color: String = "#00000000"
					override var colorRes: Int = 0
					override var drawableRes: Int = 0
					override var show: DividerDefine.Position.() -> Int = { -0x00000003 }
				}.let {
					block(it)
					when
					{
						it.drawableRes != 0 -> context.getDrawable(it.drawableRes)
						it.colorRes != 0 -> ColorDrawable(context.getColor(it.colorRes))
						else -> ColorDrawable(Color.parseColor(it.color))
					}.let { drawable ->
						divider = Group.Divider(
								sizePx = it.sizeDp.toPx(),
								drawable = drawable,
								show = it.show(DividerDefine.Position)
						)
					}
				}
			}.run {
				block()
				adapter.add(Group(
						tag = tag,
						model = model as ModelInternal,
						viewProvider = view,
						onBind = onBind,
						placeholderProvider = placeholderProvider,
						onPlaceHolder = onPlaceHolder,
						divider = divider)
				)
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
			spaceDecoration.orientation = orientation
			dividerDecoration.orientation = orientation
			this@NemoRecyclerView.layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
			this@NemoRecyclerView.adapter = adapter
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
	            val onPlaceHolder: (NemoRecyclerView.ViewHolder.(binding: ViewBinding) -> Unit)?,
	            val divider: Divider?
	) : Layer(tag)
	{
		override val size: Int
			get() = if (model.isNotEmpty) model.size else if (placeholderProvider != null) 1 else 0
		
		data class Divider(val sizePx: Int,
		                   val drawable: Drawable,
		                   val show: Int,
		                   val isFirst: Boolean = true,
		                   val isLast: Boolean = true)
		{
			val width get() = if (drawable.intrinsicWidth != -1) drawable.intrinsicWidth else sizePx
			val height get() = if (drawable.intrinsicHeight != -1) drawable.intrinsicHeight else sizePx
			
			private fun Int.beginning() = this and 0x00000001 == 0
			private fun Int.middle() = this and 0x00000002 == 0
			private fun Int.end() = this and 0x00000004 == 0
			
			val drawBeginning get() = isFirst && show.beginning()
			val drawEnd get() = !isLast && show.middle() || isLast && show.end()
			
			val includePlaceholder get() = show and 0x00000008 == 0
			
			val keepAlpha get() = show and 0x00000010 == 0
			val keepPosition get() = show and 0x00000020 == 0
		}
	}
	
	class Space(tag: Any) : Layer(tag)
	{
		override val size: Int = 1
	}
}

private class Adapter : RecyclerView.Adapter<NemoRecyclerView.ViewHolder>(), NemoRecyclerView.GroupArrange
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
			override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = layerAtPosition(positionStart).let { layer ->
				if (itemCount > 0
						&& layer is Group
						&& layer.model.isEmpty
						&& layer.placeholderProvider != null)
				{
					notifyItemInserted(positionStart)
				}
				reorder()
			}
			
			override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = layerAtPosition(positionStart).let { layer ->
				if (itemCount > 0
						&& layer is Group
						&& layer.model.size == itemCount
						&& layer.placeholderProvider != null)
				{
					notifyItemRemoved(positionStart)
				}
				reorder()
			}
		})
	}
	
	fun add(group: Group)
	{
		layers[group.tag] = group
		layerOrder += group
		layerPosition[count] = group
		layerArrayForViewType += group
		group.model.adapter = this
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
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = layerOfViewType(viewType).let { layer ->
		when (layer)
		{
			is Group ->
			{
				when
				{
					viewType.isNormalViewType() ->
					{
						layer.viewProvider(LayoutInflater.from(parent.context), parent, false).also { binding ->
							if (layer.divider != null) binding.root.setTag(VIEW_TAG_DIVIDER, layer.divider)
						}
					}
					layer.placeholderProvider != null ->
					{
						layer.placeholderProvider.invoke(LayoutInflater.from(parent.context), parent, false).also { binding ->
							if (layer.divider != null && layer.divider.includePlaceholder) binding.root.setTag(VIEW_TAG_DIVIDER, layer.divider)
						}
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
	
	override fun onBindViewHolder(holder: NemoRecyclerView.ViewHolder, position: Int) = layerAtPosition(position).let { layer ->
		when (layer)
		{
			is Group ->
			{
				val modelPosition = position - layer.model.adapterPosition
				
				/* set divider */
				holder.binding.root.getTag(VIEW_TAG_DIVIDER)?.also { divider ->
					divider as Group.Divider
					holder.binding.root.setTag(VIEW_TAG_DIVIDER,
							divider.copy(
									isFirst = modelPosition == 0,
									isLast = modelPosition == layer.model.lastIndex)
					)
				}
				
				/* callback */
				if (layer.model.isNotEmpty)
				{
					holder.modelPosition = modelPosition
					layer.onBind(holder, layer.model[modelPosition], holder.binding)
				}
				else if (layer.onPlaceHolder != null)
				{
					holder.modelPosition = modelPosition /* maybe always 0 */
					layer.onPlaceHolder.invoke(holder, holder.binding)
				}
			}
			is Space -> TODO("not implemented")
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
 * ItemDecoration
 *###################################################################################################################################*/
private const val VIEW_TAG_SPACE = 0x0D34A130
private const val VIEW_TAG_DIVIDER = 0x0D34A131

private class SpaceDecoration : RecyclerView.ItemDecoration()
{
	@RecyclerView.Orientation
	var orientation: Int = RecyclerView.VERTICAL
	
	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State)
	{
		// TODO: 2020-04-01 "not implemented"
	}
	
	override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State)
	{
		super.onDraw(c, parent, state)
		// TODO: 2020-04-01 "not implemented"
	}
}

private class DividerDecoration : RecyclerView.ItemDecoration()
{
	@RecyclerView.Orientation
	var orientation: Int = RecyclerView.VERTICAL
	
	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) = (view.getTag(VIEW_TAG_DIVIDER) as? Group.Divider)?.let { divider ->
		if (orientation == DividerItemDecoration.VERTICAL)
			outRect.set(0, 0, 0, divider.height)
		else
			outRect.set(0, 0, divider.width, 0)
	} ?: Unit.also {
		outRect.set(0, 0, 0, 0)
	}
	
	override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State)
	{
		if (parent.layoutManager == null) return
		
		canvas.save()
		if (orientation == DividerItemDecoration.VERTICAL)
		{
			val left: Int
			val right: Int
			val bounds = Rect()
			if (parent.clipToPadding)
			{
				left = parent.paddingLeft
				right = parent.width - parent.paddingRight
				canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
			}
			else
			{
				left = 0
				right = parent.width
			}
			parent.children.forEach { child ->
				(child.getTag(VIEW_TAG_DIVIDER) as? Group.Divider)?.let { divider ->
					parent.getDecoratedBoundsWithMargins(child, bounds)
					val bottom = bounds.bottom + if (divider.keepPosition) 0 else child.translationY.roundToInt()
					val top = bottom - divider.height
					if (!divider.keepAlpha) divider.drawable.alpha = (child.alpha * 255).toInt()
					divider.drawable.setBounds(left, top, right, bottom)
					divider.drawable.draw(canvas)
				}
			}
		}
		else
		{
			val top: Int
			val bottom: Int
			val bounds = Rect()
			if (parent.clipToPadding)
			{
				top = parent.paddingTop
				bottom = parent.height - parent.paddingBottom
				canvas.clipRect(parent.paddingLeft, top, parent.width - parent.paddingRight, bottom)
			}
			else
			{
				top = 0
				bottom = parent.height
			}
			parent.children.forEach { child ->
				(child.getTag(VIEW_TAG_DIVIDER) as? Group.Divider)?.let { divider ->
					parent.getDecoratedBoundsWithMargins(child, bounds)
					val right = bounds.right + if (divider.keepPosition) 0 else child.translationX.roundToInt()
					val left = right - divider.width
					if (!divider.keepAlpha) divider.drawable.alpha = (child.alpha * 255).toInt()
					divider.drawable.setBounds(left, top, right, bottom)
					divider.drawable.draw(canvas)
				}
			}
		}
		canvas.restore()
	}
}

/*###################################################################################################################################
 * Model
 *###################################################################################################################################*/
private interface ModelInternal
{
	val size: Int
	var adapter: Adapter?
	var adapterPosition: Int
	
	operator fun get(index: Int): Any?
	fun keyAt(index: Int): Any
}

private val ModelInternal.isEmpty: Boolean
	get() = size == 0

private val ModelInternal.isNotEmpty: Boolean
	get() = size > 0

private val ModelInternal.lastIndex: Int
	get() = size - 1

private class SingletonImpl<M>(value: M, private val key: M.() -> Any?) : ModelInternal, NemoRecyclerView.Model.Singleton<M>
{
	override val size: Int = 1
	override var adapter: Adapter? = null
	override var adapterPosition: Int = 0
	override var value: M = value
		set(value)
		{
			if (field != value)
			{
				field = value
				adapter?.notifyItemChanged(adapterPosition)
			}
		}
	
	override fun get(index: Int): Any? = value
	override fun keyAt(index: Int): Any = value.key() ?: 0
}

private class ListImpl<M>(private val list: List<M>, private val key: M.() -> Any?) : ModelInternal, NemoRecyclerView.Model.List<M>, List<M> by list
{
	override var adapter: Adapter? = null
	override var adapterPosition: Int = 0
	
	override fun keyAt(index: Int): Any = this[index].key() ?: index
}

private class MutableListImpl<M>(private val list: MutableList<M>, private val key: M.() -> Any?) : ModelInternal, NemoRecyclerView.Model.MutableList<M>, MutableList<M> by list
{
	private var estimatedSize = -1
	
	override val size: Int
		get() = if (estimatedSize >= 0) estimatedSize else list.size
	override var adapter: Adapter? = null
	override var adapterPosition: Int = 0
	
	override fun keyAt(index: Int): Any = this[index].key() ?: index
	
	override fun add(element: M): Boolean
	{
		val index = size
		return list.add(element).alsoIf(true) {
			adapter?.notifyItemInserted(adapterPosition + index)
		}
	}
	
	override fun add(index: Int, element: M)
	{
		list.add(index, element)
		adapter?.notifyItemInserted(adapterPosition + index)
	}
	
	override fun addAll(index: Int, elements: Collection<M>): Boolean
	{
		return list.addAll(index, elements).alsoIf(true) {
			adapter?.notifyItemRangeInserted(adapterPosition + index, elements.size)
		}
	}
	
	override fun addAll(elements: Collection<M>): Boolean
	{
		val index = size
		return list.addAll(elements).alsoIf(true) {
			adapter?.notifyItemRangeInserted(adapterPosition + index, elements.size)
		}
	}
	
	override fun clear()
	{
		val count = size
		list.clear()
		adapter?.notifyItemRangeRemoved(adapterPosition, count)
	}
	
	override fun remove(element: M): Boolean
	{
		val index = list.indexOf(element)
		return list.remove(element).alsoIf(true) {
			adapter?.notifyItemRemoved(adapterPosition + index)
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
				adapter?.notifyItemRangeRemoved(range.position, range.count)
			}
		}
	}
	
	override fun removeAt(index: Int): M
	{
		return list.removeAt(index).also {
			adapter?.notifyItemRemoved(adapterPosition + index)
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
				adapter?.notifyItemRangeRemoved(range.position, range.count)
			}
		}
	}
	
	override fun set(index: Int, element: M): M
	{
		return list.set(index, element).also { prevElement ->
			if (prevElement != element)
			{
				adapter?.notifyItemChanged(adapterPosition + index)
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
					adapter?.notifyItemRangeChanged(range.position, range.count)
				}
			}
			clear()
		}
		
		fun MutableList<Range>.update(lIndex: Int, lElement: M, rElement: M)
		{
			if (lElement != rElement)
			{
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
		
		estimatedSize = elements.size
		var lIndex = 0
		var lRealIndex = 0
		val change = mutableListOf<Range>()
		val insert = mutableListOf<M>()
		elements.forEach { rElement ->
			var lMark = list.findSameKey(lRealIndex, rElement.key())
			if (lMark != -1) lMark += lIndex - lRealIndex
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
						adapter?.notifyItemRangeInserted(adapterPosition + lIndex, insert.size)
						lIndex += insert.size
						insert.clear()
					}
					change.update(lIndex, list[lRealIndex], rElement)
					++lIndex
					++lRealIndex
				}
				else /* lMark > lIndex */ ->
				{
					change.submit()
					if (insert.isNotEmpty())
					{
						adapter?.notifyItemRangeInserted(adapterPosition + lIndex, insert.size)
						lIndex += insert.size
						lMark += insert.size
						insert.clear()
					}
					val count = lMark - lIndex
					adapter?.notifyItemRangeRemoved(adapterPosition + lIndex, count)
					lRealIndex += count
					change.update(lIndex, list[lRealIndex], rElement)
					++lIndex
					++lRealIndex
				}
			}
		}
		change.submit()
		if (insert.isNotEmpty())
		{
			adapter?.notifyItemRangeInserted(adapterPosition + lIndex, insert.size)
			lIndex += insert.size
		}
		if (lRealIndex < list.size)
		{
			val count = list.size - lRealIndex
			adapter?.notifyItemRangeRemoved(adapterPosition + lIndex, count)
		}
		list.clear()
		list.addAll(elements)
		estimatedSize = -1
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
