package nks.griplockiot.util

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import nks.griplockiot.model.Hole

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