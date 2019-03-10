package nks.griplockiot.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nks.griplockiot.model.*
import nks.griplockiot.repository.CourseRepository
import nks.griplockiot.repository.PlayerRepository

class GameViewModel(private val courseRepository: CourseRepository,
                    private val playerRepository: PlayerRepository) : ViewModel() {

    private var dummyCourseScore = MutableLiveData<List<CourseScore>>()

    fun getDummyCourseScoresList(): LiveData<List<CourseScore>> {
        val hole = mutableListOf(Hole(1, 3, 50), Hole(2, 3, 50), Hole(3, 3, 50))
        val course = Course("TestCourse", 68, 2000, hole, null, null)
        val player = Player("TestPlayer")
        val courseScore = CourseScore(1, course, player)
        courseScore.holeScores = listOf(HoleScore(1, 3),
                HoleScore(2, 3),
                HoleScore(3, 4))
        dummyCourseScore.value = listOf(courseScore)
        return dummyCourseScore
    }

}