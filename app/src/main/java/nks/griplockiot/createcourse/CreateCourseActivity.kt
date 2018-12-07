package nks.griplockiot.createcourse

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_course.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import nks.griplockiot.R

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
