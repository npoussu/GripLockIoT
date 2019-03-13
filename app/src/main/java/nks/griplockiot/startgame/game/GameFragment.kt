package nks.griplockiot.startgame.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_game.*
import nks.griplockiot.R
import nks.griplockiot.data.GamePlayerAdapter
import nks.griplockiot.viewmodel.GameViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel

class GameFragment : Fragment() {

    // TODO: Load hole details from DB using the ARG_INDEX

    lateinit var adapter: GamePlayerAdapter

    private val viewModel: GameViewModel by sharedViewModel()

    companion object {

        private const val ARG_INDEX = "gameFragment_index"

        fun newInstance(index: Int): GameFragment {
            val args = Bundle()
            args.putSerializable(ARG_INDEX, index)
            val fragment = GameFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        game_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val fragmentIndex = arguments!!.getInt("gameFragment_index")

        adapter = GamePlayerAdapter()

        game_recyclerview.adapter = adapter

        viewModel.getDummyCourseScoresList().observe(this, Observer {
            adapter.setCourseScores(it)
        })

        // TODO: Move string to resources, fix lint error

        viewModel.getHoleListLiveData().observe(this, Observer {
            holeID.text = "Hole: " + it[fragmentIndex - 1].hole.toString()
            holeLength.text = "Length: " + it[fragmentIndex - 1].length.toString()
            holePar.text = "Par: " + it[fragmentIndex - 1].par.toString()
        })
    }

}