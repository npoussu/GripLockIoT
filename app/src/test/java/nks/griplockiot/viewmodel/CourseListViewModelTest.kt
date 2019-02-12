package nks.griplockiot.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole
import nks.griplockiot.repository.CourseRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CourseListViewModelTest {

    @get: Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CourseListViewModel

    private lateinit var dataResponseList: MutableLiveData<List<Course>>
    private lateinit var dataResponse: MutableLiveData<Course>

    private val courseIndex = 5

    @Mock
    lateinit var observerList: Observer<List<Course>>

    @Mock
    lateinit var observer: Observer<Course>

    @Mock
    lateinit var repository: CourseRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataResponseList = MutableLiveData()
        dataResponse = MutableLiveData()
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

    private fun createDummyCourse(): Course {
        return Course("test", 60, 18,
                listOf(Hole(1, 3, 60), Hole(1, 3, 60))
                , null, null)
    }

}