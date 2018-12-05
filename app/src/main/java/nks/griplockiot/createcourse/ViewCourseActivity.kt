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
import nks.griplockiot.model.Course

class ViewCourseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_course)

        val course = intent.extras["course"] as Course

        toolbar_activity_view_course.title = "Course: " + course.name

        course_list_view_course_activity.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)

        val adapter = HoleAdapter(course.holes, onClickListener = { view, hole ->
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose par / hole length")

            val view = layoutInflater.inflate(R.layout.dialog_number_picker, null)

            val numberPickerPar = view.findViewById(R.id.numberPickerPar) as NumberPicker
            val numberPickerLength = view.findViewById(R.id.numberPickerLength) as NumberPicker

            numberPickerPar.minValue = 3
            numberPickerPar.maxValue = 5

            numberPickerLength.minValue = 50
            numberPickerLength.maxValue = 500

            builder.setView(view)

            builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                Toast.makeText(applicationContext, "OK clicked, Par: " + numberPickerPar.value.toString() + "Length: " + numberPickerLength.value.toString(), Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        })

        course_list_view_course_activity.adapter = adapter

    }

}
