package nks.griplockiot.repository

import androidx.lifecycle.LiveData
import nks.griplockiot.database.CourseDAO
import nks.griplockiot.model.Course

class CourseRepository(courseDAO: CourseDAO) {

    private val courseDAOImpl: CourseDAO = courseDAO

    fun getCourseList(): LiveData<List<Course>> {
        return courseDAOImpl.getLiveDataCourses()
    }

    fun getCourse(id: Int): LiveData<Course> {
        return courseDAOImpl.getLiveDataCourse(id)
    }

    fun deleteCourse(course: Course) {
        courseDAOImpl.delete(course)
    }

    fun insertCourse(course: Course) {
        courseDAOImpl.insert(course)
    }

    fun updateCourse(course: Course) {
        courseDAOImpl.update(course)
    }


}