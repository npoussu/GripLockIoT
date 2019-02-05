package nks.griplockiot.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import nks.griplockiot.R
import nks.griplockiot.createcourse.CreateCourseActivity
import nks.griplockiot.startgame.StartGameActivity
import nks.griplockiot.viewscorecards.ViewScoreCardsActivity

/**
 * MainActivity: The entry point of the application and main hub
 * Contains buttons to navigate to the main parts of the application
 */
class MainActivity : AppCompatActivity() {

    private val positiveButtonClick = { _: DialogInterface, _: Int ->
        finish()
        System.exit(0)
    }

    private val negativeButtonClick = { dialog: DialogInterface, _: Int ->
        dialog.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startGame.setOnClickListener {
            val intent = Intent(this, StartGameActivity::class.java)
            startActivity(intent)
        }

        createCourse.setOnClickListener {
            val intent = Intent(this, CreateCourseActivity::class.java)
            startActivity(intent)
        }

        scoreCards.setOnClickListener {
            val intent = Intent(this, ViewScoreCardsActivity::class.java)
            startActivity(intent)
        }
        exit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("Are you sure?")
                setPositiveButton(android.R.string.yes, DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton(android.R.string.no, DialogInterface.OnClickListener(function = negativeButtonClick))
                show()
            }
        }
    }
}
