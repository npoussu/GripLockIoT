package nks.griplockiot.createcourse

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_course.*
import nks.griplockiot.R

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuAddCourse -> Toast.makeText(applicationContext, "You clicked menu add course", Toast.LENGTH_SHORT).show()
            //TODO: Add the course adding functionality here
        }
        return super.onOptionsItemSelected(item)
    }
}
