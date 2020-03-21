package kr.co.plasticcity.nemo.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kr.co.plasticcity.nemo.widget.NemoRecyclerView
import kr.co.plasticcity.nemo.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		ActivityMainBinding.inflate(layoutInflater).also { binding ->
			setContentView(binding.root)
			val header = NemoRecyclerView.model<String?>(null) { this }
			val accounts = NemoRecyclerView.model<String>(mutableListOf()) { hashCode() }
			header.value = "header"
			accounts.add("item1")
			accounts.add("item2")
			accounts.add("item3")
			val groups = recyclerView(orientation = RecyclerView.VERTICAL, reverseLayout = false) {
				useSnap = false
				group(model = header, view = ActivityMainBinding::inflate, tag = "header") {
					bind { data, binding ->
						println(adapterPosition)
						println(modelPosition)
					}
					divider {
						sizeDp = 3
						color = "#ff00ff00"
						colorRes = R.color.colorPrimary
						drawableRes = R.drawable.ic_launcher_background
						show = { BEGINNING and MIDDLE and END }
					}
				}
				space {
					sizeDp = 100
					color = "#ff00ff00"
					colorRes = R.color.colorPrimary
					fillViewport = true
					fillWeight = 1f
				}
				group(model = accounts, view = ActivityMainBinding::inflate) {
					allowDragAndDrop = false
					allowSwipeToDismiss = false
					bind { data, binding ->
						println(adapterPosition)
						println(modelPosition)
					}
					placeHolder { binding ->
						println(adapterPosition)
						println(modelPosition)
					}
				}
			}
			groups.bringForward("header")
			groups.bringToFront("header")
			groups.sendBackward("header")
			groups.sendToBack("header")
			groups.swap("header", "list")
		}
	}
}
