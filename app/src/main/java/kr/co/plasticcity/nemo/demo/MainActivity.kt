package kr.co.plasticcity.nemo.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.plasticcity.nemo.widget.NemoRecyclerView
import kr.co.plasticcity.nemo.demo.databinding.ActivityMainBinding
import kr.co.plasticcity.nemo.demo.databinding.ItemHeaderBinding
import kr.co.plasticcity.nemo.demo.databinding.ItemListBinding
import kr.co.plasticcity.nemo.demo.databinding.ItemListHeaderBinding

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
		val header = NemoRecyclerView.model(Header("NemoRecyclerView 데모", "쉽고 빠른 멀티 타입 리사이클러뷰 만들기!"))
		val listHeader = NemoRecyclerView.model(ItemHeader("아이템 갯수: 0"))
		val list = NemoRecyclerView.model(mutableListOf<Item>())
		
		super.onCreate(savedInstanceState)
		ActivityMainBinding.inflate(layoutInflater).also { binding ->
			setContentView(binding.root)
			recyclerView {
				group(header, ItemHeaderBinding::inflate) {
				
				}
				group(listHeader, ItemListHeaderBinding::inflate) {
				
				}
				group(list, ItemListBinding::inflate) {
				
				}
			}
		}
	}
}

private data class Header(val title: String, val content: String)
private data class ItemHeader(val text: String)
private data class Item(val text: String)