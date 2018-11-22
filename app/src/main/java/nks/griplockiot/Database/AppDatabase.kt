package nks.griplockiot.Database

import android.arch.persistence.room.Database
import nks.griplockiot.Model.Course

@Database(entities = [Course::class], version = 1, exportSchema = false)
abstract class AppDatabase {
    abstract fun getCourseDAO(): CourseDAO
}