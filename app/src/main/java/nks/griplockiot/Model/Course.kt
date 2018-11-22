package nks.griplockiot.Model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "course")
data class Course(
        @PrimaryKey
        val id: Int,
        val holes: ArrayList<Hole>
)