package nks.griplockiot.model

/*
* CourseScore: Data class that represents the total score for a given course & player
* */
data class CourseScore(private val ID: Int,
                       private val course: Course,
                       private val player: Player) {

    private val holeScores: List<HoleScore> = ArrayList()

    //TODO: Add createScoreBoard method that creates a list of final scores
}