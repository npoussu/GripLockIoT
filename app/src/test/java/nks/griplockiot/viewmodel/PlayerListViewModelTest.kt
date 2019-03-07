package nks.griplockiot.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import nks.griplockiot.model.Player
import nks.griplockiot.repository.PlayerRepository
import nks.griplockiot.util.Event
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PlayerListViewModelTest {

    @get: Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PlayerListViewModel

    private lateinit var dataResponseList: MutableLiveData<List<Player>>
    private lateinit var dataResponse: MutableLiveData<Player>
    private lateinit var dataResponseEvent: MutableLiveData<Event<Int>>

    @Mock
    lateinit var observerList: Observer<List<Player>>

    @Mock
    lateinit var observerEvent: Observer<Event<Int>>

    @Mock
    lateinit var observer: Observer<Player>

    @Mock
    lateinit var repository: PlayerRepository

    @Mock
    lateinit var event: Event<Int>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataResponseList = MutableLiveData()
        dataResponse = MutableLiveData()
        dataResponseEvent = MutableLiveData()
        viewModel = PlayerListViewModel(repository)
    }

    @Test
    fun `test viewModel gets observable player list`() {
        val returnedPlayerList = createDummyPlayerList()
        dataResponseList.value = returnedPlayerList

        Mockito.`when`(repository.getPlayerList()).thenReturn(dataResponseList)

        viewModel.getPlayerList().observeForever(observerList)

        verify(observerList).onChanged(listOf(Player("Sulava Operaattori"),
                Player("Silikkikasi"),
                Player("Vasymaton Taistelija"),
                Player("Wainman"),
                Player("Pauli McPeto")))

    }

    @Test
    fun `test viewModel gets observable player by id`() {

        val returnedCourse = createDummyPlayerList()
        dataResponse.value = returnedCourse[0]

        Mockito.`when`(repository.getPlayer(Mockito.anyInt())).thenReturn(dataResponse)

        viewModel.getPlayer(54).observeForever(observer)

        verify(observer).onChanged(Player("Sulava Operaattori"))
    }

    @Test
    fun `test viewModel calls repository delete course`() {

        val returnedPlayerList = createDummyPlayerList()
        viewModel.deletePlayer(returnedPlayerList[0])

        verify(repository, times(1)).deletePlayer(returnedPlayerList[0])

    }

    @Test
    fun `test viewModel calls repository update player`() {

        val returnedPlayerList = createDummyPlayerList()
        viewModel.updatePlayer(returnedPlayerList[0])

        verify(repository, times(1)).updatePlayer(returnedPlayerList[0])

    }

    @Test
    fun `test viewModel calls repository insert player`() {

        val returnedPlayerList = createDummyPlayerList()
        viewModel.insertPlayer(returnedPlayerList[0])

        verify(repository, times(1)).insertPlayer(returnedPlayerList[0])

    }

    @Test
    fun `test viewModel receives showDialog event`() {

        val clickEvent = event
        dataResponseEvent.value = clickEvent

        viewModel.showDialog.observeForever(observerEvent)

        viewModel.showDeleteDialog(event)

        verify(observerEvent).onChanged(event)
    }

    private fun createDummyPlayerList(): List<Player> {
        return listOf(Player("Sulava Operaattori"),
                Player("Silikkikasi"),
                Player("Vasymaton Taistelija"),
                Player("Wainman"),
                Player("Pauli McPeto"))
    }

}