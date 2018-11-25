package nks.griplockiot.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nks.griplockiot.R
import nks.griplockiot.model.Course

class CourseAdapter(val courseList: ArrayList<Course>) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Show some yhteenveto of course here, maybe par total yms
        holder.name.text = courseList[position].name
        holder.parTotal.text = courseList[position].parTotal.toString()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
        val parTotal = itemView.findViewById<TextView>(R.id.parTotal)

    }
}


