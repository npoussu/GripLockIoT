package nks.griplockiot.startgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_start_game.*
import nks.griplockiot.CourseListViewModel
import nks.griplockiot.R
import nks.griplockiot.data.CourseAdapterLiveData
import nks.griplockiot.model.Course
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * StartGameActivity: In progress
 */
class StartGameActivity : AppCompatActivity() {

    private val viewModel: CourseListViewModel by viewModel()

    companion object {
        const val TAG = "StartGameActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        course_recycler_view.layoutManager = LinearLayoutManager(this)
        course_recycler_view.setHasFixedSize(true)

        val adapter = CourseAdapterLiveData()
        course_recycler_view.adapter = adapter

        // 1st argument, Lifecycle owner
        viewModel.getCourseList().observe(this, Observer {
            adapter.setNotes(it)
        })

        adapter.setOnItemClickListener(object : CourseAdapterLiveData.OnItemClickListener {
            override fun deleteCourseAtPos(pos: Int) {
                viewModel.deleteCourse(adapter.getCourseAt(pos))
            }

            override fun onItemClick(course: Course) {

            }

        })
    }
}
