package nks.griplockiot.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "player")
data class Player(@ColumnInfo
                  val name: String) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @Ignore
    var selected: Boolean? = false

    override fun toString(): String {
        return "Player: $name, ID: $id Selected: $selected\n"
    }
}