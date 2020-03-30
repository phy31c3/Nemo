package kr.co.plasticcity.nemo.demo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.plasticcity.nemo.demo.databinding.*
import kr.co.plasticcity.nemo.widget.NemoRecyclerView

class MainActivity : AppCompatActivity()
{
	val colors = listOf(
			"#EF9A9A",
			"#F5B7B1",
			"#C39BD3",
			"#7FB3D5",
			"#F9E79F",
			"#7DCEA0",
			"#F8C471",
			"#F0B27A",
			"#EDBB99",
			"#F7F9F9",
			"#D5DBDB",
			"#ABB2B9",
			"#B2BABB",
			"#85C1E9",
			"#BB8FCE",
			"#A9DFBF",
			"#FAE5D3",
			"#F1C40F",
			"#4FC3F7",
			"#AED581"
	)
	
	override fun onCreate(savedInstanceState: Bundle?)
	{
		val header = NemoRecyclerView.model(Header("NemoRecyclerView 데모", "쉽고 빠른 멀티 타입 리사이클러뷰 만들기!")) { title }
		val listHeader = NemoRecyclerView.model(ItemHeader("아이템 갯수: 0"))
		val list = NemoRecyclerView.model<Item>(mutableListOf()) { key }
		
		super.onCreate(savedInstanceState)
		ActivityMainBinding.inflate(layoutInflater).also { binding ->
			setContentView(binding.root)
			recyclerView {
				group(header, ItemHeaderBinding::inflate) {
					bind { data, binding ->
						binding.title.text = data.title
						binding.content.text = data.content
					}
				}
				group(listHeader, ItemListHeaderBinding::inflate) {
					val maxIndex = 100
					bind { data, binding ->
						binding.text.text = data.text
						binding.genButton.setOnClickListener {
							val newList = mutableListOf<Item>()
							for (i in 0..maxIndex)
							{
								if (Math.random() < 0.5)
								{
									newList.add(Item(i, (Math.random() * 20).toInt()))
								}
							}
							list.update(newList)
							listHeader.value = ItemHeader("아이템 갯수: ${list.size}")
						}
						binding.addItemButton.setOnClickListener {
							val newItem = Item((Math.random() * (maxIndex + 1)).toInt(), (Math.random() * 20).toInt())
							list.indexOfFirst { it.key == newItem.key }.let {
								if (it != -1)
								{
									list[it] = newItem
									null
								}
								else it
							}?.also {
								list.indexOfFirst { it.key > newItem.key }.also {
									if (it != -1) list.add(it, newItem)
									else list += newItem
								}
							}
							listHeader.value = ItemHeader("아이템 갯수: ${list.size}")
						}
						binding.deleteItemButton.setOnClickListener {
							if (list.isEmpty()) return@setOnClickListener
							val index = (Math.random() * list.size).toInt()
							list.removeAt(index)
							listHeader.value = ItemHeader("아이템 갯수: ${list.size}")
						}
						binding.deleteAllItemButton.setOnClickListener {
							list.clear()
							listHeader.value = ItemHeader("아이템 갯수: ${list.size}")
						}
						binding.deleteMultiItemButton.setOnClickListener {
							val newList = mutableListOf<Item>()
							for (i in list.indices)
							{
								if (Math.random() < 0.5)
								{
									newList.add(list[i])
								}
							}
							list.removeAll(newList)
							listHeader.value = ItemHeader("아이템 갯수: ${list.size}")
						}
						binding.retainMultiItemButton.setOnClickListener {
							val newList = mutableListOf<Item>()
							for (i in list.indices)
							{
								if (Math.random() < 0.5)
								{
									newList.add(list[i])
								}
							}
							list.retainAll(newList)
							listHeader.value = ItemHeader("아이템 갯수: ${list.size}")
						}
					}
				}
				group(list, ItemListBinding::inflate) {
					bind { data, binding ->
						binding.root.setBackgroundColor(Color.parseColor(colors[data.color]))
						binding.text.text = "${data.key}"
					}
					placeholder(ItemListPlaceholderBinding::inflate)
				}
				group(NemoRecyclerView.model("null"), ItemFooterBinding::inflate) {
					/* empty */
				}
			}
		}
	}
}

private data class Header(val title: String, val content: String)
private data class ItemHeader(val text: String)
private data class Item(val key: Int, val color: Int)