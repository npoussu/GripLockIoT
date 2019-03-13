package nks.griplockiot.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import nks.griplockiot.database.PlayerDAO
import nks.griplockiot.model.Player

class PlayerRepository(playerDAO: PlayerDAO) {

    private val playerDAOImpl: PlayerDAO = playerDAO

    fun getPlayerList(): LiveData<List<Player>> {
        return playerDAOImpl.getPlayers()
    }

    fun getPlayer(id: Int): LiveData<Player> {
        return playerDAOImpl.getPlayer(id)
    }

    fun deletePlayer(player: Player): Job {
        return GlobalScope.launch(Dispatchers.Default) {
            playerDAOImpl.delete(player)
        }
    }

    fun insertPlayer(player: Player): Job {
        return GlobalScope.launch(Dispatchers.Default) {
            playerDAOImpl.insert(player)
        }
    }

    fun updatePlayer(player: Player): Job {
        return GlobalScope.launch(Dispatchers.Default) {
            playerDAOImpl.update(player)
        }
    }
}