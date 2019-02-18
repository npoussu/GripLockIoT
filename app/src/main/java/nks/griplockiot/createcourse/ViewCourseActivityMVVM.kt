package nks.griplockiot.createcourse

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_view_course.*
import nks.griplockiot.R
import nks.griplockiot.createcourse.googlemaps.MapsActivity
import nks.griplockiot.data.HoleAdapterMVVM
import nks.griplockiot.model.Hole
import nks.griplockiot.viewmodel.CourseListViewModel
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * ViewCourseActivity: Used to view a single course with detailed information
 * Clicking a hole opens up a NumberPicker that can be used to modify hole details
 */
class ViewCourseActivityMVVM : AppCompatActivity() {

    private val viewModel: CourseListViewModel by viewModel()
    private lateinit var adapter: HoleAdapterMVVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)

        val parTotalHeader = resources.getString(R.string.parTotalHeader)
        val lengthTotalHeader = resources.getString(R.string.lengthHeader)

        setSupportActionBar(toolbar_activity_view_course)

        course_list_view_course_activity.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        adapter = HoleAdapterMVVM()
        course_list_view_course_activity.adapter = adapter

        // Testcourse ffud id = 3
        viewModel.getCourse(3).observe(this, Observer {
            adapter.setCourse(it)
            supportActionBar?.title = "Course: " + it.name
            supportActionBar?.subtitle = parTotalHeader + " " + it.parTotal + " | " + lengthTotalHeader + " " + it.lengthTotal + " " + "m"
        })

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

