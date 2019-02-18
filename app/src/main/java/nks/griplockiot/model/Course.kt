package nks.griplockiot.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Course: Data class for holding data about a course, implements Serializable
 */
@Entity(tableName = "course")
data class Course(@ColumnInfo
                  val name: String,
                  @ColumnInfo
                  var parTotal: Int,
                  @ColumnInfo
                  var lengthTotal: Int,
                  @ColumnInfo
                  val holes: List<Hole>,
        // Latitude & Longitude are optional (nullable essentially), therefore marked as Optional type (in Kotlin
        // data types are non-nullable by default
                  @ColumnInfo
                  var latitude: Double?,
                  @ColumnInfo
                  var longitude: Double?
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}