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
import nks.griplockiot.data.HoleAdapter
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
        addCourses(holeIndex)

        course_list_create_course.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val adapter = HoleAdapter(courseListCreateCourse)
        course_list_create_course.adapter = adapter

        holes.text = holeIndex.toString()

        minusButton.setOnClickListener {
            if (holeIndex > 0) {
                holeIndex--
                holes.text = holeIndex.toString()
                // Remove last item from RecyclerView
                courseListCreateCourse.removeAt(courseListCreateCourse.size - 1)
                // Update adapters last index item
                adapter.notifyItemRemoved(courseListCreateCourse.size)
            } else {
                Toast.makeText(context, "Course must contain atleast one hole", Toast.LENGTH_SHORT).show()
            }
        }

        plusButton.setOnClickListener {
            if (holeIndex < 36) {
                holeIndex++
                holes.text = holeIndex.toString()
                addCourse(adapter.itemCount + 1)
                course_list_create_course.scrollToPosition(courseListCreateCourse.size - 1)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Course must contain a maximum of 36 holes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_create_course, container, false)
    }

    private fun addCourses(holes: Int) {
        for (i in 1..holes) {
            with(courseListCreateCourse) {
                add(Hole(i, 3, 63))
            }
        }
    }

    private fun addCourse(index: Int) {
        courseListCreateCourse.add(Hole(index, 3, 50))
    }
}