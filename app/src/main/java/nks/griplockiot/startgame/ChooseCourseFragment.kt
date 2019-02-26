package nks.griplockiot.startgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_choose_course.*
import nks.griplockiot.R
import nks.griplockiot.data.CourseAdapter
import nks.griplockiot.viewmodel.CourseListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ChooseCourseFragment : Fragment() {

    private val viewModel: CourseListViewModel by viewModel()
    private lateinit var adapter: CourseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_choose_course, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_choose_course.title = "Choose course"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        choose_course_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        choose_course_recyclerview.setHasFixedSize(true)

        adapter = CourseAdapter()
        choose_course_recyclerview.adapter = adapter

        // 1st argument, Lifecycle owner
        viewModel.getCourseList().observe(this, Observer {
            adapter.setCourses(it)
        })

        adapter.setOnItemClickListener(object : CourseAdapter.OnItemClickListener {
            override fun onClick(pos: Int) {
                //viewModel.chooseCourse(Event(pos))
                val fm = activity!!.supportFragmentManager.beginTransaction()
                fm.replace(R.id.fragment_holder, ChoosePlayerFragment())
                fm.addToBackStack(null)
                fm.commit()
            }

            override fun onLongClick(pos: Int) {
                //viewModel.showDeleteDialog(Event(pos))
            }
        })
    }
}