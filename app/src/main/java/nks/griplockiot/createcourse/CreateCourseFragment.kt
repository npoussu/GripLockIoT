package nks.griplockiot.createcourse

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_create_course.*
import nks.griplockiot.R
import nks.griplockiot.data.CourseAdapter
import nks.griplockiot.model.Hole

class CreateCourseFragment : Fragment() {
    private val courseListCreateCourse: ArrayList<Hole> = ArrayList()
    private var holeIndex: Int = 18

    companion object {
        fun newInstance(): CreateCourseFragment {
            return CreateCourseFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addCourses()

        course_list_create_course.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val adapter = CourseAdapter(courseListCreateCourse)
        course_list_create_course.adapter = adapter

        holes.text = holeIndex.toString()

        minusButton.setOnClickListener {
            if (holeIndex > 0) {
                holeIndex--
                holes.text = holeIndex.toString()
            } else {
                Toast.makeText(context, "Course must contain atleast one hole", Toast.LENGTH_SHORT).show()
            }
        }

        plusButton.setOnClickListener {
            if (holeIndex < 36) {
                holeIndex++
                holes.text = holeIndex.toString()
            } else {
                Toast.makeText(context, "Course must contain a maximum of 36 holes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_create_course, container, false)
    }

    private fun addCourses() {
        with(courseListCreateCourse) {
            add(Hole(1, 3, 33))
            add(Hole(2, 4, 60))
            add(Hole(3, 5, 73))
            add(Hole(4, 4, 153))
            add(Hole(5, 5, 55))
            add(Hole(6, 3, 87))
            add(Hole(7, 5, 55))
            add(Hole(8, 3, 187))
            add(Hole(9, 3, 151))
            add(Hole(10, 3, 33))
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