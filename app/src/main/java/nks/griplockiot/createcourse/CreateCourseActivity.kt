package nks.griplockiot.createcourse

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

    private lateinit var course: Course
    private lateinit var list: List<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_course)
        title = "Create a new course!"

        val courseArrayList: ArrayList<Hole> = arrayListOf()
        courseArrayList.add(Hole(1, 3, 33))
        courseArrayList.add(Hole(2, 3, 34))
        courseArrayList.add(Hole(3, 3, 35))

        // Toast.makeText(this, "no db" + courseArrayList.toString(), Toast.LENGTH_SHORT).show()

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
                Toast.makeText(applicationContext, "You clicked menu add course, inserting to DB", Toast.LENGTH_SHORT).show()
                thread {
                    AppDatabase.getInstance(this).getCourseDAO().insert(course)
                }
            }
            R.id.queryDB -> {
                thread {
                    list = AppDatabase.getInstance(this).getCourseDAO().getCourses()
                }
            }
            R.id.selectDB -> {
                Toast.makeText(this, list[0].holes.toString(), Toast.LENGTH_SHORT).show()
                Toast.makeText(this, list.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
