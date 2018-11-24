package nks.griplockiot.database

import android.arch.persistence.room.*
import nks.griplockiot.model.Course

@Dao
interface CourseDAO {

    @Insert
    fun insert(vararg course: Course)

    @Update
    fun update(vararg course: Course)

    @Delete
    fun delete(vararg course: Course)

    @Query("SELECT * FROM course")
    fun getCourses(): ArrayList<Course>
}
