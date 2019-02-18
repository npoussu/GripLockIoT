package nks.griplockiot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nks.griplockiot.R
import nks.griplockiot.model.Course
import nks.griplockiot.model.Hole

/**
 * HoleAdapter: Holds a list of Hole-objects
 */
class HoleAdapterMVVM : RecyclerView.Adapter<HoleAdapterMVVM.ViewHolder>() {

    private var holeList: List<Hole> = ArrayList()
    private lateinit var courseVal: Course
    private lateinit var listenerClass: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hole_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return holeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = holeList[position]
        holder.hole?.text = item.hole.toString()
        holder.par?.text = item.par.toString()
        holder.length?.text = item.length.toString() + "m"
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hole = itemView.findViewById<TextView?>(R.id.hole)
        val length = itemView.findViewById<TextView?>(R.id.length)
        val par = itemView.findViewById<TextView?>(R.id.par)

        init {
            itemView.setOnClickListener {
                listenerClass.onClick(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        listenerClass = listener
    }

    fun setCourse(course: Course) {
        // TODO: notifyDataSetChanged is relatively slow (refreshes whole RecyclerView, refactor to faster
        courseVal = course
        holeList = course.holes
        notifyDataSetChanged()
    }

    fun getCourse(): Course {
        return courseVal
    }
}


