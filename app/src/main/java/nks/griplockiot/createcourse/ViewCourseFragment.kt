package nks.griplockiot.createcourse

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_view_course.*
import nks.griplockiot.R
import nks.griplockiot.data.CourseAdapter
import nks.griplockiot.model.Hole

class ViewCourseFragment : Fragment() {
    private val courseListViewCourses: ArrayList<Hole> = ArrayList()

    companion object {
        fun newInstance(): ViewCourseFragment {
            return ViewCourseFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addCourses()

        course_list_view_course.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val adapter = CourseAdapter(courseListViewCourses)
        course_list_view_course.adapter = adapter
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.menuAddCourse)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_view_course, container, false)
    }

    private fun addCourses() {
        with(courseListViewCourses) {
            add(Hole(1, 3, 33))
            add(Hole(2, 4, 60))
            add(Hole(3, 5, 73))
            add(Hole(4, 4, 153))
            add(Hole(5, 5, 55))
            add(Hole(6, 3, 87))
            add(Hole(7, 5, 55))
            add(Hole(8, 3, 187))
            add(Hole(9, 3, 217))
            add(Hole(10, 3, 33))
            add(Hole(11, 4, 60))
            add(Hole(12, 5, 73))
            add(Hole(13, 4, 153))
            add(Hole(14, 5, 55))
            add(Hole(15, 3, 87))
            add(Hole(16, 5, 55))
            add(Hole(17, 3, 187))
            add(Hole(18, 3, 217))
        }
    }
}