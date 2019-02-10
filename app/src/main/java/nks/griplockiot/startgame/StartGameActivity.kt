package nks.griplockiot.startgame

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_start_game.*
import nks.griplockiot.CourseListViewModel
import nks.griplockiot.R
import nks.griplockiot.data.CourseAdapterLiveData
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * StartGameActivity: In progress
 */
class StartGameActivity : AppCompatActivity() {

    private val viewModel: CourseListViewModel by viewModel()
    private lateinit var adapter: CourseAdapterLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        course_recycler_view.layoutManager = LinearLayoutManager(this)
        course_recycler_view.setHasFixedSize(true)

        adapter = CourseAdapterLiveData()
        course_recycler_view.adapter = adapter

        // 1st argument, Lifecycle owner
        viewModel.getCourseList().observe(this, Observer {
            adapter.setNotes(it)
        })

        viewModel.showDialog.observe(this, Observer { clickPos ->
            clickPos.getContentIfNotHandled()?.let {
                showDeleteDialog(it)
            }
        })

        adapter.setOnItemClickListener(object : CourseAdapterLiveData.OnItemClickListener {
            override fun onClick(pos: Int) {
                Toast.makeText(applicationContext, "Open course details!", Toast.LENGTH_LONG).show()
            }

            override fun onLongClick(pos: Int) {
                viewModel.showDeleteDialog(pos)
            }
        })
    }

    private fun showDeleteDialog(clickPos: Int) {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle("Delete course?")
            setPositiveButton("yes") { dialog, _ ->
                viewModel.deleteCourse(adapter.getCourseAt(clickPos))
                dialog.dismiss()
            }
            setNegativeButton("no") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
    }
}
