package nks.griplockiot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nks.griplockiot.model.Player
import nks.griplockiot.repository.PlayerRepository
import nks.griplockiot.util.Event

class PlayerListViewModel(private val repository: PlayerRepository) : ViewModel() {

    private var dummyPlayer = MutableLiveData<List<Player>>()
    private val showDialogVal = MutableLiveData<Event<Int>>()

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

    val showDialog: LiveData<Event<Int>>
        get() = showDialogVal

    fun showDeleteDialog(event: Event<Int>) {
        showDialogVal.value = event
    }


}