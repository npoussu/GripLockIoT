package nks.griplockiot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_course.*

class CreateCourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)
        title = "Create a new course!"


        val fragmentManager = supportFragmentManager
        val fragmentAdapter = CoursePagerAdapter(fragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)

    }

}
