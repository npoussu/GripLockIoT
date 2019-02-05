package nks.griplockiot.database

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import nks.griplockiot.model.Course
import nks.griplockiot.util.Converters

/**
 * AppDatabase: Implementation of the abstract class RoomDatabase
 */
@Database(entities = [Course::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCourseDAO(): CourseDAO

    // Singleton instance database, destroy db on migration
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