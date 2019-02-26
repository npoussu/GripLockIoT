package nks.griplockiot.startgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_choose_player.*
import nks.griplockiot.R

class StartGameActivity : AppCompatActivity(), ChooseCourseFragment.OnCourseSelectedListener {

    override fun onCourseSelected(index: Int?) {
        val fragment = ChoosePlayerFragment()
        val arguments = Bundle()
        arguments.putInt("courseID", index!!)
        fragment.arguments = arguments
        val fm = supportFragmentManager.beginTransaction()
        fm.replace(R.id.fragment_holder, fragment)
        fm.addToBackStack(null)
        fm.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)
        setSupportActionBar(toolbar_choose_player)

        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragment_holder, ChooseCourseFragment())
        ft.commit()

    }

}
