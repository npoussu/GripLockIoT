package nks.griplockiot.createcourse

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_view_course.*
import nks.griplockiot.R
import nks.griplockiot.createcourse.googlemaps.MapsActivity
import nks.griplockiot.data.HoleAdapterMVVM
import nks.griplockiot.model.Course
import nks.griplockiot.util.Event
import nks.griplockiot.viewmodel.CourseListViewModel
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * ViewCourseActivity: Used to view a single course with detailed information
 * Clicking a hole opens up a NumberPicker that can be used to modify hole details
 */
class ViewCourseActivity : AppCompatActivity() {

    private val viewModel: CourseListViewModel by viewModel()
    private lateinit var adapter: HoleAdapterMVVM
    private lateinit var course: Course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)

        val courseID: Int = intent.getIntExtra("courseID", 999)

        val parTotalHeader = resources.getString(R.string.parTotalHeader)
        val lengthTotalHeader = resources.getString(R.string.lengthHeader)

        setSupportActionBar(toolbar_activity_view_course)

        course_list_view_course_activity.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter = HoleAdapterMVVM()
        course_list_view_course_activity.adapter = adapter

        viewModel.getCourse(courseID).observe(this, Observer {
            course = it
            adapter.setCourse(it)
            invalidateOptionsMenu()
            supportActionBar?.title = "Course: " + it.name
            supportActionBar?.subtitle = parTotalHeader + " " + it.parTotal + " | " + lengthTotalHeader + " " + it.lengthTotal + " " + "m"
        })

        viewModel.showNumberPickerDialog.observe(this, Observer { clickPos ->
            clickPos.getContentIfNotHandled()?.let {
                showNumberPickerDialog(it)
            }
        })

        adapter.setOnItemClickListener(object : HoleAdapterMVVM.OnItemClickListener {
            override fun onClick(pos: Int) {
                viewModel.showNumberPickerDialog(Event(pos))
            }
        })

    }

    private fun showNumberPickerDialog(clickPos: Int) {
        val builder = AlertDialog.Builder(this)

        val nullParent: ViewGroup? = null
        val view = layoutInflater.inflate(R.layout.dialog_number_picker, nullParent)

        val numberPickerPar = view.findViewById(R.id.numberPickerPar) as NumberPicker
        val numberPickerLength = view.findViewById(R.id.numberPickerLength) as NumberPicker

        numberPickerPar.minValue = 3
        numberPickerPar.maxValue = 5
        numberPickerPar.wrapSelectorWheel = true
        numberPickerPar.value = course.holes[clickPos].par

        numberPickerLength.minValue = 30
        numberPickerLength.maxValue = 500
        numberPickerPar.wrapSelectorWheel = true
        numberPickerLength.value = course.holes[clickPos].length

        builder.setView(view)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            // TODO: Move this logic to ViewModel
            course.holes[clickPos].par = numberPickerPar.value
            course.holes[clickPos].length = numberPickerLength.value
            course.parTotal = calculateTotalPar(course.holes)
            course.lengthTotal = calculateTotalLength(course.holes)
            viewModel.updateCourse(course)
            dialog.dismiss()
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_maps, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.openMapsActivity -> {
                val intent = Intent(applicationContext, MapsActivity::class.java)
                intent.putExtra("course", adapter.getCourse())
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

