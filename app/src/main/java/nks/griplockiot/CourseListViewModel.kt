package nks.griplockiot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nks.griplockiot.model.Course
import nks.griplockiot.repository.CourseRepository

class CourseListViewModel(private val repository: CourseRepository) : ViewModel() {

    private val showDialogVal = MutableLiveData<Event<Int>>()
    private val startNewActivityVal = MutableLiveData<Event<Int>>()

    val startNewActivity: LiveData<Event<Int>>
        get() = startNewActivityVal

    fun startNewActivity(pos: Int) {
        startNewActivityVal.value = Event(pos)
    }

    val showDialog: LiveData<Event<Int>>
        get() = showDialogVal

    fun showDeleteDialog(clickPos: Int) {
        showDialogVal.value = Event(clickPos)
    }

    fun getCourseList(): LiveData<List<Course>> {
        return repository.getCourseList()
    }

    fun getCourse(id: Int): LiveData<Course> {
        return repository.getCourse(id)
    }

    fun deleteCourse(course: Course) {
        repository.deleteCourse(course)
    }


}