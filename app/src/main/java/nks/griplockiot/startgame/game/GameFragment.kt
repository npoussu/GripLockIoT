package nks.griplockiot.startgame.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_game.*
import nks.griplockiot.R

class GameFragment : Fragment() {

    // TODO: Load hole details from DB using the ARG_INDEX

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

    override fun onStart() {
        super.onStart()
        game_textview.text = arguments!!.getInt("gameFragment_index").toString()
    }

}