package nks.griplockiot.createcourse

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_view_course.*
import nks.griplockiot.R
import nks.griplockiot.data.HoleAdapter
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole

class ViewCourseActivity : AppCompatActivity() {

    lateinit var course: Course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)

        course = intent.extras["course"] as Course

        toolbar_activity_view_course.title = "Course: " + course.name

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
                Toast.makeText(applicationContext, "OK clicked, Par: " + numberPickerPar.value.toString() + "Length: " + numberPickerLength.value.toString(), Toast.LENGTH_SHORT).show()
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
        super.onBackPressed()
        course.parTotal = calculateTotalPar(course.holes)
        AppDatabase.getInstance(applicationContext).getCourseDAO().update(course)
    }

    private fun calculateTotalPar(holeList: List<Hole>): Int {
        val iterator = holeList.listIterator()
        var parTotal = 0

        for (item in iterator) {
            parTotal += item.par
        }
        return parTotal
    }

}
