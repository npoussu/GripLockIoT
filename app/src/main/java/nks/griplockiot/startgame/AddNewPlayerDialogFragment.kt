package nks.griplockiot.startgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_add_player.*
import nks.griplockiot.R
import nks.griplockiot.model.Player
import nks.griplockiot.viewmodel.PlayerListViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AddNewPlayerDialogFragment : DialogFragment() {

    private val viewModel: PlayerListViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.dialog_add_player, container, false)
        return rootView
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        add_player_ok_button.setOnClickListener {
            viewModel.insertPlayer(Player(add_player_edittext.text.toString()))
            dismiss()
        }
    }

}