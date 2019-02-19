package nks.griplockiot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nks.griplockiot.createcourse.addCourses
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole
import nks.griplockiot.repository.CourseRepository
import nks.griplockiot.util.Event

class CourseListViewModel(private val repository: CourseRepository) : ViewModel() {

    private val showDialogVal = MutableLiveData<Event<Int>>()
    private val startNewActivityVal = MutableLiveData<Event<Int>>()
    private val showNumberPickerDialogVal = MutableLiveData<Event<Int>>()

    private val dummyCourse: MutableLiveData<Course> = MutableLiveData()
    private val holeIndexCounter: MutableLiveData<Int> = MutableLiveData()

    init {
        var holeList = ArrayList<Hole>()
        holeList = addCourses(18, holeList)
        val course = Course("dummyCourse", 60, 2000, holeList, null, null)
        dummyCourse.value = course

        // Default course has 18 holes
        holeIndexCounter.value = 18
    }

    val startNewActivity: LiveData<Event<Int>>
        get() = startNewActivityVal

    fun startNewActivity(event: Event<Int>) {
        startNewActivityVal.value = event
    }

    val showNumberPickerDialog: LiveData<Event<Int>>
        get() = showNumberPickerDialogVal

    fun showNumberPickerDialog(event: Event<Int>) {
        showNumberPickerDialogVal.value = event
    }

    val showDialog: LiveData<Event<Int>>
        get() = showDialogVal

    fun showDeleteDialog(event: Event<Int>) {
        showDialogVal.value = event
    }

    fun getCourseList(): LiveData<List<Course>> {
        return repository.getCourseList()
    }

    fun getDummyCourse(): LiveData<Course> {
        return dummyCourse
    }

    fun getCourse(id: Int): LiveData<Course> {
        return repository.getCourse(id)
    }

    fun deleteCourse(course: Course) {
        repository.deleteCourse(course)
    }

    fun updateCourse(course: Course) {
        repository.updateCourse(course)
    }

    fun insertCourse(course: Course) {
        repository.insertCourse(course)
    }

    fun getHoleIndex(): LiveData<Int> {
        return holeIndexCounter
    }

    fun decrementCounter() {
        holeIndexCounter.value = holeIndexCounter.value!!.minus(1)
    }

    fun incrementCounter() {
        holeIndexCounter.value = holeIndexCounter.value!!.plus(1)
    }
}