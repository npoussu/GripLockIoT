package nks.griplockiot.startgame.game

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import nks.griplockiot.R

class GameActivity : AppCompatActivity() {

    // TODO: Create Game object that holds game data and keep it at the ViewModel
    // TODO: Create ViewModel :)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val fragmentManager = supportFragmentManager

        // TODO: Get amount of holes from DB (Course.holes)
        val fragmentAdapter = GamePagerAdapter(fragmentManager, 36)
        viewpager_game.adapter = fragmentAdapter

        val courseID = intent.extras.getInt("courseID")
        val players = intent.extras.getIntegerArrayList("playerIDs")

        // Hardcoded, for testing
        Toast.makeText(this, "CourseID: " + courseID.toString() + " " + "Player IDs: " +
                players[0].toString() + "," +
                players[1].toString() + "," +
                players[2].toString() + "," +
                players[3].toString() + "," +
                players[4].toString(),
                Toast.LENGTH_LONG).show()

    }
}