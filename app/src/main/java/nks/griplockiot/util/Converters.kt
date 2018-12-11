package nks.griplockiot.util

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import nks.griplockiot.model.Hole

/**
 * Converters: Static class for converting the custom objects into primitive types suitable for
 * storing into the SQLite database.
 */
class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun courseToJson(holeList: List<Hole>?): String {
            return Gson().toJson(holeList)
        }

        @TypeConverter
        @JvmStatic
        fun jsonToCourse(holeList: String): List<Hole>? {
            val objects = Gson().fromJson(holeList, Array<Hole>::class.java) as Array<Hole>
            return objects.toList()
        }
    }
}