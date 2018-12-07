package nks.griplockiot.createcourse

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_create_course.*
import nks.griplockiot.R
import nks.griplockiot.data.HoleAdapter
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole

class CreateCourseFragment : Fragment() {

    private lateinit var list: List<Course>
    lateinit var course: Course

    private var courseListCreateCourse: ArrayList<Hole> = ArrayList()
    private var holeIndex: Int = 18

    interface RefreshInterface {
        fun refreshArrayList()
    }

    companion object {
        fun newInstance(): CreateCourseFragment {
            return CreateCourseFragment()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addCourses(holeIndex)

        setHasOptionsMenu(true)

        course_list_create_course.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        val adapter = HoleAdapter(courseListCreateCourse, onClickListener = { view, hole ->
            val builder = AlertDialog.Builder(context!!)

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
                course_list_create_course.adapter?.notifyDataSetChanged()
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        })

        course_list_create_course.adapter = adapter

        minusButton.setOnClickListener {
            if (holeIndex > 0) {
                holeIndex--
                holes.text = holeIndex.toString()
                // Remove last item from RecyclerView
                courseListCreateCourse.removeAt(courseListCreateCourse.size - 1)
                // Update adapters last index item
                adapter.notifyItemRemoved(courseListCreateCourse.size)
            } else {
                Toast.makeText(context, "Course must contain at least one hole", Toast.LENGTH_SHORT).show()
            }
        }

        plusButton.setOnClickListener {
            if (holeIndex < 36) {
                //TODO: Make adapter transitions smoother
                holeIndex++
                holes.text = holeIndex.toString()
                addCourse(adapter.itemCount + 1)
                course_list_create_course.scrollToPosition(courseListCreateCourse.size - 1)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Course must contain a maximum of 36 holes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_course, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuAddCourse -> {
                AppDatabase.getInstance(context!!).getCourseDAO().insert(Course(courseNameEditText.text.toString(),
                        calculateTotalPar(courseListCreateCourse),
                        calculateTotalLength(courseListCreateCourse),
                        courseListCreateCourse))
                (activity as CreateCourseActivity).refreshArrayList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addCourses(holes: Int) {
        for (i in 1..holes) {
            with(courseListCreateCourse) {
                add(Hole(i, 3, 100))
            }
        }
    }

    private fun addCourse(index: Int) {
        courseListCreateCourse.add(Hole(index, 3, 100))
    }

    private fun calculateTotalPar(holeList: ArrayList<Hole>): Int {
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