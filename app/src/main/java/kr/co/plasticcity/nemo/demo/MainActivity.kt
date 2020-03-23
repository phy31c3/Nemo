package kr.co.plasticcity.nemo.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.plasticcity.nemo.widget.NemoRecyclerView
import kr.co.plasticcity.nemo.demo.databinding.ActivityMainBinding
import kr.co.plasticcity.nemo.demo.databinding.ItemHeaderBinding

class MainActivity : AppCompatActivity()
{
	private val header = NemoRecyclerView.model<Header?>(Header("헤더의 제목", "헤더의 내용"))
	
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		ActivityMainBinding.inflate(layoutInflater).also { binding ->
			setContentView(binding.root)
			recyclerView {
				group(header, ItemHeaderBinding::inflate) {
					bind { data, binding ->
						TODO()
					}
				}
			}
		}
	}
	
	fun onDataReceived()
	{
		TODO()
	}
}

private data class Header(val title: String, val content: String)
