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
					bind { data, binding ->
						binding.text.text = data.text
						binding.genButton.setOnClickListener {
							val newList = mutableListOf<Item>()
							for (i in 0..19)
							{
								if (Math.random() < 0.5)
								{
									newList.add(Item(i))
								}
							}
							list.update(newList)
							listHeader.value = ItemHeader("아이템 갯수: ${list.size}")
						}
						binding.addItemButton.setOnClickListener {
						
						}
						binding.deleteItemButton.setOnClickListener {
						
						}
					}
				}
				group(list, ItemListBinding::inflate) {
					bind { data, binding ->
						binding.root.setBackgroundColor(Color.parseColor(colors[data.key]))
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
private data class Item(val key: Int)