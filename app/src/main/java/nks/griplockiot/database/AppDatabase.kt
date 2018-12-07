package nks.griplockiot.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import android.support.annotation.WorkerThread
import nks.griplockiot.model.Course
import nks.griplockiot.util.Converters

@Database(entities = [Course::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCourseDAO(): CourseDAO

    // TODO: Change running Room on UI Thread to other thread

    companion object {

        private var INSTANCE: AppDatabase? = null

        // Call getInstance only on worker threads
        @WorkerThread
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "course")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return INSTANCE as AppDatabase
        }
    }

}