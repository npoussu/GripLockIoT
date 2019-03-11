package nks.griplockiot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hadilq.liveevent.LiveEvent
import nks.griplockiot.model.*
import nks.griplockiot.repository.CourseRepository
import nks.griplockiot.repository.PlayerRepository

class GameViewModel(private val courseRepository: CourseRepository,
                    private val playerRepository: PlayerRepository,
                    ID: Int) : ViewModel() {

    private val dummyCourseScore = MutableLiveData<List<CourseScore>>()

    private val currentCourse: LiveData<Course> = courseRepository.getCourse(ID)

    private val holeList: LiveData<List<Hole>> = Transformations.map(currentCourse) { currentCourse ->
        currentCourse.holes
    }


    fun getDummyCourseScoresList(): LiveData<List<CourseScore>> {
        val hole = mutableListOf(Hole(1, 3, 50), Hole(2, 3, 50), Hole(3, 3, 50))
        val course = Course("TestCourse", 68, 2000, hole, null, null)
        val player = Player("TestPlayer")
        val courseScore = CourseScore(1, course, player)
        courseScore.holeScores = listOf(HoleScore(1, 3),
                HoleScore(2, 3),
                HoleScore(3, 4))
        dummyCourseScore.value = listOf(courseScore, courseScore, courseScore)
        return dummyCourseScore
    }

    fun getSingleCourse(id: Int): LiveData<Course> {
        val livedata = courseRepository.getCourse(id)
        return livedata.toSingleEvent()
    }

    fun getCurrentCourse() = currentCourse

    fun getHoleListLiveData() = holeList

}

fun <T> LiveData<T>.toSingleEvent(): LiveData<T> {
    val result = LiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}