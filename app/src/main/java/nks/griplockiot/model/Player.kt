package nks.griplockiot.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "player")
data class Player(@ColumnInfo
                  val name: String) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}