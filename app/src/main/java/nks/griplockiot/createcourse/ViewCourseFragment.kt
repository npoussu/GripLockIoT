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
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole

class ViewCourseFragment : Fragment() {
    private val holeList: ArrayList<Hole> = ArrayList()
    private val courseListViewCourses: ArrayList<Course> = ArrayList()

    companion object {
        fun newInstance(): ViewCourseFragment {
            return ViewCourseFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
/*
        val courseListSize: Int = AppDatabase.getInstance(activity!!.applicationContext).getCourseDAO().getCourses().size

        for (i in 0 until courseListSize - 1) {
            courseListViewCourses += AppDatabase.getInstance(activity!!.applicationContext).getCourseDAO().getCourses()[i].holes
        }
        */

        // TODO: Create utils functions for creating dummy courses and hole objects

        holeList.add(Hole(1, 2, 55))
        holeList.add(Hole(2, 4, 155))
        holeList.add(Hole(3, 3, 205))
        holeList.add(Hole(4, 5, 187))
        holeList.add(Hole(5, 3, 93))

        val iterator = holeList.listIterator()
        var parTotal = 0

        for (item in iterator) {
            parTotal += item.par
        }

        courseListViewCourses.add(Course("Murhaniemi", parTotal, holeList))


        // TODO: Show course yhteenveto and show details on click, long click to delete a course
        course_list_view_course.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        //val adapter = HoleAdapter(courseListViewCourses)

        val arrayList: ArrayList<Course> = ArrayList(AppDatabase.getInstance(activity!!.applicationContext).getCourseDAO().getCourses())

        val adapter = CourseAdapter(arrayList)
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
}
