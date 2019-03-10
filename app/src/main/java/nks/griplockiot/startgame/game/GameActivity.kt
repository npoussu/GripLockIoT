package nks.griplockiot.startgame.game

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import nks.griplockiot.R
import nks.griplockiot.viewmodel.GameViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class GameActivity : AppCompatActivity() {

    private val viewModel: GameViewModel by viewModel()

    companion object {
        private val TAG = GameActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        setSupportActionBar(toolbar_game)

        supportActionBar!!.title = "Course: XYZ"
        supportActionBar!!.subtitle = "Holes: XY"

        val fragmentManager = supportFragmentManager

        // TODO: Get amount of holes from DB (Course.holes)
        val fragmentAdapter = GamePagerAdapter(fragmentManager, 36)
        viewpager_game.adapter = fragmentAdapter

        val courseID = intent.extras.getInt("courseID")
        val players = intent.extras.getIntegerArrayList("playerIDs")

        Log.i(TAG, courseID.toString())


        for (i in 0 until players.size) {
            Log.i(TAG, players[i].toString())
        }

    }
}