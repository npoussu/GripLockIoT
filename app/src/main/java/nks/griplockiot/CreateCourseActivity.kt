package nks.griplockiot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_create_course.*
import nks.griplockiot.Model.Hole

class CreateCourseActivity : AppCompatActivity() {

    val courses: ArrayList<Hole> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)
        title = "Create a new course!"
        // TODO: Center the text in toolbar
        addCourses()

        course_list.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val adapter = CourseAdapter(courses)
        course_list.adapter = adapter
    }

    fun addCourses() {
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
