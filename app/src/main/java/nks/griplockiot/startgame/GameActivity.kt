package nks.griplockiot.startgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import nks.griplockiot.R

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val fragmentManager = supportFragmentManager
        val fragmentAdapter = GamePagerAdapter(fragmentManager)
        viewpager_game.adapter = fragmentAdapter

    }
}