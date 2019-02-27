package nks.griplockiot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nks.griplockiot.R
import nks.griplockiot.model.Player

/**
 * PlayerAdapter: Holds a list of Player-objects
 */
class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    private var playerList: List<Player> = ArrayList()
    private lateinit var listenerImpl: PlayerAdapter.OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = playerList[position]
        holder.playerName?.text = item.name
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName = itemView.findViewById<TextView?>(R.id.player_name)
        val checkBox = itemView.findViewById<CheckBox?>(R.id.player_selected)

        init {
            itemView.setOnClickListener {
                listenerImpl.onClick(adapterPosition)
                checkBox!!.isChecked = !checkBox.isChecked
            }
            itemView.setOnLongClickListener {
                listenerImpl.onLongClick(adapterPosition)
                true
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
        fun onLongClick(pos: Int)
    }

    fun setOnItemClickListener(listener: PlayerAdapter.OnItemClickListener) {
        listenerImpl = listener
    }

    fun setPlayer(player: List<Player>) {
        // TODO: notifyDataSetChanged is relatively slow (refreshes whole RecyclerView, refactor to faster
        playerList = player
        notifyDataSetChanged()
    }

    fun getPlayerAt(pos: Int): Player {
        return playerList[pos]
    }

}


