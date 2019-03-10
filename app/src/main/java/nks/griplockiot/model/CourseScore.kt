package nks.griplockiot.model

/*
* CourseScore: Data class that represents the total score for a given course & player
* */
data class CourseScore(val ID: Int,
                       val course: Course,
                       val player: Player) {

    var holeScores: List<HoleScore> = ArrayList()

    //TODO: Add createScoreBoard method that creates a list of final scores
}