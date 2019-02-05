package nks.griplockiot.createcourse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_course.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import nks.griplockiot.R

/**
 * CreateCourseActivity: A blank Activity to hold the Fragments ViewCourse / CreateCourse-fragment
 * Implements a RefreshInterface that is used to refresh data in the ViewCourseFragment
 * from CreateCourseFragment
 */
class CreateCourseActivity : AppCompatActivity(), CreateCourseFragment.RefreshInterface {

    @ObsoleteCoroutinesApi
    override fun refreshArrayList() {
        val fragment = supportFragmentManager.fragments[1] as ViewCourseFragment
        fragment.refreshArrayListFragment()
    }

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
