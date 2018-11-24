package nks.griplockiot.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import nks.griplockiot.R
import nks.griplockiot.StartGameActivity
import nks.griplockiot.ViewScoreCardsActivity
import nks.griplockiot.createcourse.CreateCourseActivity

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
