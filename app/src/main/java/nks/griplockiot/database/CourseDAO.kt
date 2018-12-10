package nks.griplockiot.database

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import nks.griplockiot.model.Course

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

    @Query("SELECT * FROM course WHERE id = :id")
    fun getCourse(id: Int): Course

}
