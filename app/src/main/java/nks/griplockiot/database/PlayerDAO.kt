package nks.griplockiot.database

import androidx.lifecycle.LiveData
import androidx.room.*
import nks.griplockiot.model.Player

@Dao
interface PlayerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg player: Player)

    @Update
    fun update(vararg player: Player)

    @Delete
    fun delete(vararg player: Player)

    @Query("SELECT * FROM player")
    fun getPlayers(): LiveData<List<Player>>

    @Query("SELECT * FROM player WHERE id = :id")
    fun getPlayer(id: Int): LiveData<Player>

}