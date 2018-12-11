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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import nks.griplockiot.R
import nks.griplockiot.data.HoleAdapter
import nks.griplockiot.database.AppDatabase
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole

/**
 * CreateCourseFragment: Used to create new courses. The user can modify the amount of holes
 * and modify the details by clicking the correspondent cells in the RecyclerView.
 * Clicking the menu button collapses the menu that contains a button that can be used to insert
 * new entities to the database.
 */
class CreateCourseFragment : Fragment(), CoroutineScope {

    lateinit var course: Course

    private var courseListCreateCourse: ArrayList<Hole> = ArrayList()
    private var holeIndex: Int = 18

    @ObsoleteCoroutinesApi
    override
    val coroutineContext = newFixedThreadPoolContext(2, "bg")

    interface RefreshInterface {
        fun refreshArrayList()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Create the initial list of courses
        addCourses(holeIndex)

        setHasOptionsMenu(true)

        course_list_create_course.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        /** Create the adapter that holds the list of Holes.
         *   Higher order function onClickListener that inflates a NumberPicker that can be used to
         *   modify / select par / length values.
         */
        val adapter = HoleAdapter(courseListCreateCourse, onClickListener = { _, hole ->
            val builder = AlertDialog.Builder(context!!)

            val view = View.inflate(context, R.layout.dialog_number_picker, null)

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
                course_list_create_course.adapter?.notifyDataSetChanged()
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
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
                holeIndex++
                holes.text = holeIndex.toString()
                addCourse(adapter.itemCount + 1)
                adapter.notifyDataSetChanged()
                course_list_create_course.smoothScrollToPosition(courseListCreateCourse.size - 1)
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

    @ObsoleteCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuAddCourse -> {
                // Run the insert a new course query on background thread
                // Calculate total par / length for the course, omit location details
                runBlocking(coroutineContext) {
                    AppDatabase.getInstance(context!!).getCourseDAO().insert(Course(courseNameEditText.text.toString(),
                            calculateTotalPar(courseListCreateCourse),
                            calculateTotalLength(courseListCreateCourse),
                            courseListCreateCourse, null, null))
                }
                (activity as CreateCourseActivity).refreshArrayList()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Create a dummy list of Holes
    private fun addCourses(holes: Int) {
        for (i in 1..holes) {
            with(courseListCreateCourse) {
                add(Hole(i, 3, 100))
            }
        }
    }

    // Add a single Hole to the Course ArrayList
    private fun addCourse(index: Int) {
        courseListCreateCourse.add(Hole(index, 3, 100))
    }

    // Calculate total par of the course
    private fun calculateTotalPar(holeList: ArrayList<Hole>): Int {
        val iterator = holeList.listIterator()
        var parTotal = 0

        for (item in iterator) {
            parTotal += item.par
        }
        return parTotal
    }

    // Calculate total length of the course
    private fun calculateTotalLength(holeList: List<Hole>): Int {
        val iterator = holeList.listIterator()
        var lengthTotal = 0

        for (item in iterator) {
            lengthTotal += item.length
        }
        return lengthTotal
    }
}