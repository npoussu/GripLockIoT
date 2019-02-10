package nks.griplockiot.createcourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_course.*
import nks.griplockiot.R

/**
 * CreateCourseActivity: A blank Activity to hold the Fragments ViewCourse / CreateCourse-fragment
 * Implements a RefreshInterface that is used to refresh data in the ViewCourseFragment
 * from CreateCourseFragment
 */
class CreateCourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)

        title = "Create a course!"

        val fragmentManager = supportFragmentManager
        val fragmentAdapter = CoursePagerAdapter(fragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)
    }
}
