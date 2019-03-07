package nks.griplockiot.startgame

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_choose_player.*
import nks.griplockiot.R
import nks.griplockiot.data.PlayerAdapter
import nks.griplockiot.startgame.game.GameActivity
import nks.griplockiot.util.Event
import nks.griplockiot.viewmodel.PlayerListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ChoosePlayerFragment : Fragment() {

    private val TAG = ChoosePlayerFragment::class.java.simpleName

    lateinit var adapter: PlayerAdapter

    private val viewModel: PlayerListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_choose_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_choose_player.title = "Choose player(s)"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val arguments = arguments
        val courseID = arguments!!.getInt("courseID")
        Toast.makeText(context, courseID.toString(), Toast.LENGTH_LONG).show()

        choose_player_recyclerview.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        adapter = PlayerAdapter()

        choose_player_recyclerview.adapter = adapter

        viewModel.getPlayerList().observe(this, Observer {
            adapter.setPlayer(it)
        })

        viewModel.showDialog.observe(this, Observer { clickPos ->
            clickPos.getContentIfNotHandled()?.let {
                showDeleteDialog(it)
            }
        })

        create_new_player.setOnClickListener {
            val dialog = AddNewPlayerDialogFragment()
            dialog.show(fragmentManager!!, "addNewPlayerDialogFragment")
        }

        start_new_game.setOnClickListener {
            Toast.makeText(context, "Start new game", Toast.LENGTH_LONG).show()
            val intent = Intent(this@ChoosePlayerFragment.context, GameActivity::class.java)
            intent.putExtra("courseID", courseID)
            intent.putExtra("playerIDs", adapter.getSelectedPlayers())
            activity!!.startActivity(intent)
        }

        adapter.setOnItemClickListener(object : PlayerAdapter.OnItemClickListener {
            override fun onClick(pos: Int) {
                // do nothing
            }

            override fun onLongClick(pos: Int) {
                viewModel.showDeleteDialog(Event(pos))
            }
        })

    }

    private fun showDeleteDialog(clickPos: Int) {
        val builder = AlertDialog.Builder(context)
        with(builder) {
            setTitle("Delete Player?")
            setPositiveButton("yes") { dialog, _ ->
                viewModel.deletePlayer(adapter.getPlayerAt(clickPos))
                dialog.dismiss()
            }
            setNegativeButton("no") { dialog, _ ->
                dialog.cancel()
            }
            show()
        }
    }


}