package nks.griplockiot.database

import android.arch.persistence.room.Database
import nks.griplockiot.model.Course

@Database(entities = [Course::class], version = 1, exportSchema = false)
abstract class AppDatabase {
    abstract fun getCourseDAO(): CourseDAO
}