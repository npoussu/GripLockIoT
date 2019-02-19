package nks.griplockiot.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole
import nks.griplockiot.repository.CourseRepository
import nks.griplockiot.util.Event
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CourseListViewModelTest {

    companion object {
        const val courseIndex = 5
        const val parTotal = 60
        const val lengthTotal = 1800
        const val hole = 1
        const val par = 3
        const val length = 60
    }

    @get: Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CourseListViewModel

    private lateinit var dataResponseList: MutableLiveData<List<Course>>
    private lateinit var dataResponse: MutableLiveData<Course>
    private lateinit var dataResponseEvent: MutableLiveData<Event<Int>>

    @Mock
    lateinit var observerList: Observer<List<Course>>

    @Mock
    lateinit var observer: Observer<Course>

    @Mock
    lateinit var observerEvent: Observer<Event<Int>>

    @Mock
    lateinit var repository: CourseRepository

    @Mock
    lateinit var event: Event<Int>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataResponseList = MutableLiveData()
        dataResponse = MutableLiveData()
        dataResponseEvent = MutableLiveData()
        viewModel = CourseListViewModel(repository)
    }

    @Test
    fun `test viewModel gets observable course list`() {
        val returnedCourse = createDummyCourse()
        dataResponseList.value = listOf(returnedCourse)

        Mockito.`when`(repository.getCourseList()).thenReturn(dataResponseList)

        viewModel.getCourseList().observeForever(observerList)

        verify(observerList).onChanged(listOf(Course(returnedCourse.name,
                returnedCourse.parTotal,
                returnedCourse.lengthTotal,
                returnedCourse.holes,
                returnedCourse.latitude,
                returnedCourse.longitude)))

    }

    @Test
    fun `test viewModel gets observable course by id`() {

        val returnedCourse = createDummyCourse()
        dataResponse.value = returnedCourse

        Mockito.`when`(repository.getCourse(Mockito.anyInt())).thenReturn(dataResponse)

        viewModel.getCourse(courseIndex).observeForever(observer)

        verify(observer).onChanged(Course(returnedCourse.name,
                returnedCourse.parTotal,
                returnedCourse.lengthTotal,
                returnedCourse.holes,
                returnedCourse.latitude,
                returnedCourse.longitude))
    }

    @Test
    fun `test viewModel calls repository delete course`() {

        val dummyCourse = createDummyCourse()
        viewModel.deleteCourse(dummyCourse)

        verify(repository, times(1)).deleteCourse(dummyCourse)

    }

    @Test
    fun `test viewModel calls repository update course`() {

        val dummyCourse = createDummyCourse()
        viewModel.updateCourse(dummyCourse)

        verify(repository, times(1)).updateCourse(dummyCourse)

    }

    @Test
    fun `test viewModel calls repository insert course`() {

        val dummyCourse = createDummyCourse()
        viewModel.insertCourse(dummyCourse)

        verify(repository, times(1)).insertCourse(dummyCourse)

    }

    @Test
    fun `test viewModel receives startNewActivity event`() {

        val clickEvent = event
        dataResponseEvent.value = clickEvent

        viewModel.startNewActivity.observeForever(observerEvent)

        viewModel.startNewActivity(event)

        verify(observerEvent).onChanged(event)
    }

    @Test
    fun `test viewModel receives showDialog event`() {

        val clickEvent = event
        dataResponseEvent.value = clickEvent

        viewModel.showDialog.observeForever(observerEvent)

        viewModel.showDeleteDialog(event)

        verify(observerEvent).onChanged(event)
    }

    private fun createDummyCourse(): Course {
        return Course("test", parTotal, lengthTotal,
                mutableListOf(Hole(hole, par, length), Hole(hole, par, length))
                , null, null)
    }

}