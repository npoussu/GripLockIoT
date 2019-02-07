package nks.griplockiot.database

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import nks.griplockiot.model.Course

/**
 * CourseDAO: Data access object for accessing the DB entity Course
 */
@Dao
interface CourseDAO {

    @Insert(onConflict = REPLACE)
    fun insert(vararg course: Course)

    @Update
    fun update(vararg course: Course)

    @Delete
    fun delete(vararg course: Course)

    @Query("SELECT * FROM course")
    fun getCourses(): List<Course>

    @Query("SELECT * FROM course")
    fun getLiveDataCourses(): LiveData<List<Course>>

    @Query("SELECT * FROM course WHERE id = :id")
    fun getLiveDataCourse(id: Int): LiveData<Course>

    @Query("SELECT * FROM course WHERE id = :id")
    fun getCourse(id: Int): Course

}
