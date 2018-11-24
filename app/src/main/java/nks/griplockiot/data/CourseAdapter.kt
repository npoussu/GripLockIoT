package nks.griplockiot.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nks.griplockiot.R
import nks.griplockiot.model.Hole

class CourseAdapter(val courseList: ArrayList<Hole>) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.hole.text = courseList[position].hole.toString()
        holder.par.text = courseList[position].par.toString()
        holder.length.text = courseList[position].length.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hole = itemView.findViewById<TextView>(R.id.hole)
        val length = itemView.findViewById<TextView>(R.id.length)
        val par = itemView.findViewById<TextView>(R.id.par)

    }
}


