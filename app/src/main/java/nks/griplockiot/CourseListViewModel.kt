package nks.griplockiot

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import nks.griplockiot.model.Course
import nks.griplockiot.repository.CourseRepository

class CourseListViewModel(repository: CourseRepository) : ViewModel() {

    // TODO: Create delete, update, insert methods w/ observables rxjava, thread yms

    private val repository: CourseRepository = repository

    fun getCourseList(): LiveData<List<Course>> {
        return repository.getCourseList()
    }

    fun deleteCourse(course: Course) {
        repository.deleteCourse(course)
    }


}