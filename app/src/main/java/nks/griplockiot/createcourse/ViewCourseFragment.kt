package nks.griplockiot.createcourse

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.fragment_view_course.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import nks.griplockiot.R
import nks.griplockiot.data.CourseAdapter
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import java.io.Serializable

class ViewCourseFragment : Fragment(), CoroutineScope {

    lateinit var adapter: CourseAdapter
    lateinit var arrayList: ArrayList<Course>

    override
    val coroutineContext = newFixedThreadPoolContext(2, "bg")

    companion object {
        fun newInstance(): ViewCourseFragment {
            return ViewCourseFragment()
        }
    }

    fun refreshArrayListFragment() {
        runBlocking(coroutineContext) {
            arrayList.clear()
            arrayList = ArrayList(AppDatabase.getInstance(activity!!.applicationContext).getCourseDAO().getCourses())
        }
        adapter.updateData(arrayList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        course_list_view_course.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        runBlocking(coroutineContext) {
            arrayList = ArrayList(AppDatabase.getInstance(activity!!.applicationContext).getCourseDAO().getCourses())
            adapter = CourseAdapter(arrayList, onClickListener = { view, course ->
                val intent = Intent(context, ViewCourseActivity::class.java)
                intent.putExtra("course", course as Serializable)
                startActivity(intent)
            }, onLongClickListener = { view, course ->
                adapter.deleteItem(course)
                AppDatabase.getInstance(context!!).getCourseDAO().delete(course)
            })
            course_list_view_course.adapter = adapter
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.menuAddCourse)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        runBlocking(coroutineContext) {
            arrayList.clear()
            arrayList = ArrayList(AppDatabase.getInstance(activity!!.applicationContext).getCourseDAO().getCourses())
        }
        adapter.updateData(arrayList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_course, container, false)
    }
}
