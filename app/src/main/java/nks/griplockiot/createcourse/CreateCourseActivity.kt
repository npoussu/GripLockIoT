package nks.griplockiot.createcourse

import android.arch.persistence.room.Room
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_create_course.*
import nks.griplockiot.R
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole
import kotlin.concurrent.thread

class CreateCourseActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var course: Course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)
        title = "Create a new course!"

        database = Room.databaseBuilder(this, AppDatabase::class.java, "course").build()

        val courseArrayList: ArrayList<Hole> = arrayListOf()
        courseArrayList.add(Hole(1, 3, 33))
        courseArrayList.add(Hole(2, 3, 34))
        courseArrayList.add(Hole(3, 3, 35))

        course = Course(courseArrayList)

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
            R.id.menuAddCourse -> {
                Toast.makeText(applicationContext, "You clicked menu add course", Toast.LENGTH_SHORT).show()
                //TODO: Add the course adding functionality here
                thread {
                    database.getCourseDAO().insert(course)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
