package nks.griplockiot.createcourse

import android.os.Bundle
import android.view.*
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_create_course.*
import kotlinx.coroutines.ObsoleteCoroutinesApi
import nks.griplockiot.R
import nks.griplockiot.data.HoleAdapterMVVM
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole
import nks.griplockiot.util.Event
import nks.griplockiot.viewmodel.CourseListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * CreateCourseFragment: Used to create new courses. The user can modify the amount of holes
 * and modify the details by clicking the correspondent cells in the RecyclerView.
 * Clicking the menu button collapses the menu that contains a button that can be used to insert
 * new entities to the database.
 */
class CreateCourseFragment : Fragment() {

    lateinit var course: Course
    lateinit var adapter: HoleAdapterMVVM

    private var courseListCreateCourse: ArrayList<Hole> = ArrayList()
    private var holeIndex: Int = 18

    private val viewModel: CourseListViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        course_list_create_course.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        adapter = HoleAdapterMVVM()

        course_list_create_course.adapter = adapter

        viewModel.getDummyCourse().observe(this, Observer {
            course = it
            adapter.setCourse(it)
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

        // TODO: Refactor minus / plusbutton
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
                addCourse(adapter.itemCount + 1, courseListCreateCourse)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    @ObsoleteCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuAddCourse -> {
                // Run the insert a new course query on background thread
                // Calculate total par / length for the course, omit location details
                // viewmodel insert here
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showNumberPickerDialog(clickPos: Int) {
        val builder = AlertDialog.Builder(context!!)

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
            adapter.setCourse(course)
            dialog.dismiss()
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}

// Calculate total par of the course
fun calculateTotalPar(holeList: List<Hole>): Int {
    val iterator = holeList.listIterator()
    var parTotal = 0

    for (item in iterator) {
        parTotal += item.par
    }
    return parTotal
}

// Calculate total length of the course
fun calculateTotalLength(holeList: List<Hole>): Int {
    val iterator = holeList.listIterator()
    var lengthTotal = 0

    for (item in iterator) {
        lengthTotal += item.length
    }
    return lengthTotal
}

// Create a dummy list of Holes
fun addCourses(holes: Int, courseList: ArrayList<Hole>): ArrayList<Hole> {
    for (i in 1..holes) {
        with(courseList) {
            add(Hole(i, 3, 100))
        }
    }
    return courseList
}

// Add a single Hole to the Course ArrayList
fun addCourse(index: Int, courseList: ArrayList<Hole>) {
    courseList.add(Hole(index, 3, 100))
}