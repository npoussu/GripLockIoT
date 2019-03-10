package nks.griplockiot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nks.griplockiot.R
import nks.griplockiot.model.CourseScore

class GamePlayerAdapter : RecyclerView.Adapter<GamePlayerAdapter.ViewHolder>() {

    private var courseScoreList: List<CourseScore> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = courseScoreList[position]
        holder.playerName?.text = item.player.name
        holder.score?.text = item.holeScores[position].score.toString()
        holder.scoreTotal?.text = 54.toString()
        holder.minusBtn?.text = "-"
        holder.par?.text = 3.toString()
        holder.plusBtn?.text = "+"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_player_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseScoreList.size
    }

    fun setCourseScores(courses: List<CourseScore>) {
        courseScoreList = courses
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerName = itemView.findViewById<TextView?>(R.id.playerNameGame)
        val score = itemView.findViewById<TextView?>(R.id.playerScore)
        val scoreTotal = itemView.findViewById<TextView?>(R.id.playerScoreTotal)
        val minusBtn = itemView.findViewById<TextView?>(R.id.minus)
        val par = itemView.findViewById<TextView?>(R.id.playerPar)
        val plusBtn = itemView.findViewById<TextView?>(R.id.plus)

    }
}