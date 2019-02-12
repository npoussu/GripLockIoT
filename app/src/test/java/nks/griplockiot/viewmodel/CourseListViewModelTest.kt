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
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CourseListViewModelTest {

    @get: Rule
    val rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CourseListViewModel
    private lateinit var dataResponse: MutableLiveData<List<Course>>

    @Mock
    lateinit var observer: Observer<List<Course>>

    @Mock
    lateinit var repository: CourseRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataResponse = MutableLiveData()
        viewModel = CourseListViewModel(repository)
    }


    @Test
    fun `test viewModel gets observable`() {
        val returnedCourse = createDummyCourse()
        dataResponse.value = listOf(returnedCourse)

        Mockito.`when`(repository.getCourseList()).thenReturn(dataResponse)

        viewModel.getCourseList().observeForever(observer)

        verify(observer).onChanged(listOf(Course(returnedCourse.name,
                returnedCourse.parTotal,
                returnedCourse.lengthTotal,
                returnedCourse.holes,
                returnedCourse.latitude,
                returnedCourse.longitude)))

    }

    private fun createDummyCourse(): Course {
        return Course("test", 60, 18,
                listOf(Hole(1, 3, 60), Hole(1, 3, 60))
                , null, null)
    }

}