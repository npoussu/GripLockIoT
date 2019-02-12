package nks.griplockiot.createcourse

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_view_course.*
import nks.griplockiot.R
import nks.griplockiot.data.CourseAdapter
import nks.griplockiot.viewmodel.CourseListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * ViewCourseFragment: Used to view a list of existing courses
 * OnClicking a certain cell on the RecyclerView, ViewCourseActivity will be opened that shows
 * detailed information about the Course
 * Long click opens up a Dialog that prompts the user to confirm the deletion of the course.
 */

class ViewCourseFragment : Fragment() {

    private val viewModel: CourseListViewModel by viewModel()
    private lateinit var adapter: CourseAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        course_list_view_course.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        course_list_view_course.setHasFixedSize(true)

        adapter = CourseAdapter()
        course_list_view_course.adapter = adapter

        // 1st argument, Lifecycle owner
        viewModel.getCourseList().observe(this, Observer {
            adapter.setNotes(it)
        })

        viewModel.showDialog.observe(this, Observer { clickPos ->
            clickPos.getContentIfNotHandled()?.let {
                showDeleteDialog(it)
            }
        })

        viewModel.startNewActivity.observe(this, Observer { coursePos ->
            coursePos.getContentIfNotHandled()?.let {
                startNewActivity(it)
            }
        })

        adapter.setOnItemClickListener(object : CourseAdapter.OnItemClickListener {
            override fun onClick(pos: Int) {
                viewModel.startNewActivity(pos)
            }

            override fun onLongClick(pos: Int) {
                viewModel.showDeleteDialog(pos)
            }
        })
    }

    private fun startNewActivity(coursePos: Int) {
        val intent = Intent(activity, ViewCourseActivity::class.java)
        intent.putExtra("course", adapter.getCourseAt(coursePos))
        startActivity(intent)
    }

    private fun showDeleteDialog(clickPos: Int) {
        val builder = AlertDialog.Builder(context!!)
        with(builder) {
            setTitle(getString(R.string.delete_course))
            setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                viewModel.deleteCourse(adapter.getCourseAt(clickPos))
                dialog.dismiss()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menuAddCourse)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_course, container, false)
    }
}
