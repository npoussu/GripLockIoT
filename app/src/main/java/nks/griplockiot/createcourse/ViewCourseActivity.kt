package nks.griplockiot.createcourse

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.NumberPicker
import kotlinx.android.synthetic.main.activity_view_course.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import nks.griplockiot.R
import nks.griplockiot.data.HoleAdapter
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole

class ViewCourseActivity : AppCompatActivity(), CoroutineScope {

    lateinit var course: Course

    override
    val coroutineContext = newFixedThreadPoolContext(2, "bg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)

        course = intent.extras["course"] as Course

        val parTotalHeader = resources.getString(R.string.parTotalHeader)
        val lengthTotalHeader = resources.getString(R.string.lengthHeader)

        toolbar_activity_view_course.title = "Course: " + course.name
        toolbar_activity_view_course.subtitle = parTotalHeader + " " + course.parTotal + " | " + lengthTotalHeader + " " + course.lengthTotal + " " + "m"

        course_list_view_course_activity.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val adapter = HoleAdapter(course.holes, onClickListener = { view, hole ->
            val builder = AlertDialog.Builder(this)

            val view = layoutInflater.inflate(R.layout.dialog_number_picker, null)

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

            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                hole.par = numberPickerPar.value
                hole.length = numberPickerLength.value
                dialog.dismiss()
                course_list_view_course_activity.adapter?.notifyDataSetChanged()
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        })

        course_list_view_course_activity.adapter = adapter

    }

    override fun onBackPressed() {
        course.parTotal = calculateTotalPar(course.holes)
        course.lengthTotal = calculateTotalLength(course.holes)
        runBlocking(coroutineContext) {
            AppDatabase.getInstance(applicationContext).getCourseDAO().update(course)
        }
        super.onBackPressed()
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


