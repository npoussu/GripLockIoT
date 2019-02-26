package nks.griplockiot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nks.griplockiot.model.Player
import nks.griplockiot.repository.PlayerRepository

class PlayerListViewModel(private val repository: PlayerRepository) : ViewModel() {

    private var dummyPlayer = MutableLiveData<List<Player>>()

    fun getPlayerList(): LiveData<List<Player>> {
        return repository.getPlayerList()
    }

    fun getDummyPlayerList(): LiveData<List<Player>> {
        dummyPlayer.value = listOf(Player("Sulava Operaattori"),
                Player("Silikkikasi"),
                Player("Vasymaton Taistelija"),
                Player("Wainman"),
                Player("Pauli McPeto"))
        return dummyPlayer
    }

    fun getPlayer(id: Int): LiveData<Player> {
        return repository.getPlayer(id)
    }

    fun deletePlayer(player: Player) {
        repository.deletePlayer(player)
    }

    fun updatePlayer(player: Player) {
        repository.updatePlayer(player)
    }

    fun insertPlayer(player: Player) {
        repository.insertPlayer(player)
    }

}