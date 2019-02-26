package nks.griplockiot.startgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import nks.griplockiot.R

/**
 * StartGameActivity: In progress
 */
class StartGameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.choose_course_fragment_placeholder, ChooseCourseFragment())
        ft.commit()

    }
}
