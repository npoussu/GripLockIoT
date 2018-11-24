package nks.griplockiot

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_create_course.*
import nks.griplockiot.Model.Hole

class CreateCourseFragment : Fragment() {
    val courses: ArrayList<Hole> = ArrayList()

    companion object {
        fun newInstance(): CreateCourseFragment {
            return CreateCourseFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        addCourses()

        course_list.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val adapter = CourseAdapter(courses)
        course_list.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_course, container, false)
    }

    private fun addCourses() {
        courses.add(Hole(1, 3, 33))
        courses.add(Hole(2, 4, 60))
        courses.add(Hole(3, 5, 73))
        courses.add(Hole(4, 4, 153))
        courses.add(Hole(5, 5, 55))
        courses.add(Hole(6, 3, 87))
        courses.add(Hole(7, 5, 55))
        courses.add(Hole(8, 3, 187))
    }
}