package nks.griplockiot.createcourse

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.NumberPicker
import kotlinx.android.synthetic.main.activity_view_course.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import nks.griplockiot.R
import nks.griplockiot.createcourse.googlemaps.MapsActivity
import nks.griplockiot.data.HoleAdapter
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole
import java.io.Serializable

/**
 * ViewCourseActivity: Used to view a single course with detailed information
 * Clicking a hole opens up a NumberPicker that can be used to modify hole details
 */
@ObsoleteCoroutinesApi
class ViewCourseActivity : AppCompatActivity(), CoroutineScope {

    lateinit var course: Course

    @ObsoleteCoroutinesApi
    override
    val coroutineContext = newFixedThreadPoolContext(2, "bg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)

        // Get the course from the ViewCourseFragment intent extras
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        course = intent.extras["course"] as Course

        val parTotalHeader = resources.getString(R.string.parTotalHeader)
        val lengthTotalHeader = resources.getString(R.string.lengthHeader)

        setSupportActionBar(toolbar_activity_view_course)

        // Build the toolbar
        supportActionBar?.title = "Course: " + course.name
        supportActionBar?.subtitle = parTotalHeader + " " + course.parTotal + " | " + lengthTotalHeader + " " + course.lengthTotal + " " + "m"

        course_list_view_course_activity.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        // Build the RecyclerView holding the Holes, NumberPicker for modifying par / length of a
        // selected Hole
        val adapter = HoleAdapter(course.holes) { _, hole ->
            val builder = AlertDialog.Builder(this)

            val nullParent: ViewGroup? = null
            val view = layoutInflater.inflate(R.layout.dialog_number_picker, nullParent)

            val numberPickerPar = view.findViewById(R.id.numberPickerPar) as NumberPicker
            val numberPickerLength = view.findViewById(R.id.numberPickerLength) as NumberPicker

            numberPickerPar.minValue = 3
            numberPickerPar.maxValue = 5
            numberPickerPar.wrapSelectorWheel = true
            numberPickerPar.value = hole.par

            numberPickerLength.minValue = 30
            numberPickerLength.maxValue = 500
            numberPickerPar.wrapSelectorWheel = true
            numberPickerLength.value = hole.length

            builder.setView(view)

            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                hole.par = numberPickerPar.value
                hole.length = numberPickerLength.value
                dialog.dismiss()
                course_list_view_course_activity.adapter?.notifyDataSetChanged()
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
        }

        course_list_view_course_activity.adapter = adapter

    }

    @ObsoleteCoroutinesApi
    override fun onBackPressed() {
        // On exiting the Activity, update the Course with newest values to DB
        runBlocking(coroutineContext) {
            course.parTotal = calculateTotalPar(course.holes)
            course.lengthTotal = calculateTotalLength(course.holes)
            AppDatabase.getInstance(applicationContext).getCourseDAO().update(course)
        }
        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_maps, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.openMapsActivity -> {
                // Start MapsActivity and update the Course to DB
                runBlocking(coroutineContext) {
                    course.parTotal = calculateTotalPar(course.holes)
                    course.lengthTotal = calculateTotalLength(course.holes)
                    AppDatabase.getInstance(applicationContext).getCourseDAO().update(course)
                }
                val intent = Intent(applicationContext, MapsActivity::class.java)
                intent.putExtra("course", course as Serializable)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        super.onRestart()
        runBlocking(coroutineContext) {
            course = AppDatabase.getInstance(applicationContext).getCourseDAO().getCourse(course.id as Int)
        }
    }

    private fun calculateTotalPar(holeList: List<Hole>): Int {
        val iterator = holeList.listIterator()
        var parTotal = 0

        for (item in iterator) {
            parTotal += item.par
        }
        return parTotal
    }

    private fun calculateTotalLength(holeList: List<Hole>): Int {
        val iterator = holeList.listIterator()
        var lengthTotal = 0

        for (item in iterator) {
            lengthTotal += item.length
        }
        return lengthTotal
    }
}


