package nks.griplockiot.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "course")
data class Course(
        @ColumnInfo val holes: List<Hole>
) {
    @PrimaryKey(autoGenerate = true)
    var ID: Int? = null
}