package nks.griplockiot.startgame

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import nks.griplockiot.CourseListViewModel

import nks.griplockiot.R
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * StartGameActivity: In progress
 */
class StartGameActivity : AppCompatActivity() {

    private val viewModel: CourseListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        // 1st argument, Lifecycle owner
        viewModel.getCourseList().observe(this, Observer {
            Toast.makeText(this, "onChanged()", Toast.LENGTH_LONG).show()
            Log.i("jee", it[0].toString())
            Log.i("jee", it[1].toString())
            Log.i("jee", it[2].toString())
            Log.i("jee", it[3].toString())
        })
    }
}
