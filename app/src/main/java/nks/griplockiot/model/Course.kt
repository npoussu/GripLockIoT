package nks.griplockiot.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "course")
data class Course(@ColumnInfo
                  val name: String,
                  @ColumnInfo
                  var parTotal: Int,
                  @ColumnInfo
                  var lengthTotal: Int,
                  @ColumnInfo
                  val holes: List<Hole>

) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}