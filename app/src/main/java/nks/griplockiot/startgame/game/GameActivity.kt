package nks.griplockiot.startgame.game

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_game.*
import nks.griplockiot.R
import nks.griplockiot.viewmodel.GameViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class GameActivity : AppCompatActivity() {

    private val courseID: Int by lazy {
        intent.extras.getInt("courseID")
    }
    private val viewModel: GameViewModel by viewModel { parametersOf(courseID) }

    companion object {
        private val TAG = GameActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        setSupportActionBar(toolbar_game)

        val players = intent.extras.getIntegerArrayList("playerIDs")

        //TODO: Create a recyclerview row for every player #playerslist

        viewModel.getSingleCourse(courseID).observe(this, Observer {
            val fragmentManager = supportFragmentManager
            val fragmentAdapter = GamePagerAdapter(fragmentManager, it.holes.size)

            supportActionBar!!.title = "Course: " + it.name
            supportActionBar!!.subtitle = "Holes: " + it.holes.size
            viewpager_game.adapter = fragmentAdapter
        })

        viewModel.getCurrentCourse().observe(this, Observer {
            Log.i(TAG, it.toString())
        })

        viewModel.getHoleListLiveData().observe(this, Observer {
            // Log all individual holes
            for (i in 0 until it.size) {
                Log.i(TAG, it[i].toString())
            }

        })

    }
}