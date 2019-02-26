package nks.griplockiot.startgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import nks.griplockiot.R

class StartGameActivity : AppCompatActivity(), ChooseCourseFragment.OnCourseSelectedListener {

    override fun onCourseSelected(index: Int) {
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.fragment_holder, ChoosePlayerFragment())
        fm.addToBackStack(null)
        fm.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_holder, ChooseCourseFragment())
        ft.commit()

    }
}
