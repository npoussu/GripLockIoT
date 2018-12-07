package nks.griplockiot.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nks.griplockiot.R
import nks.griplockiot.model.Course

class CourseAdapter(courseList: ArrayList<Course>,
                    private val onClickListener: (View, Course) -> Unit,
                    private val onLongClickListener: (View, Course) -> Unit) : RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    var courseListClass: ArrayList<Course> = courseList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)
        return ViewHolder(view)
    }

    fun updateData(courseListNew: ArrayList<Course>) {
        courseListClass.clear()
        courseListClass.addAll(courseListNew)
        notifyDataSetChanged()
    }

    fun deleteItem(course: Course) {
        courseListClass.remove(course)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return courseListClass.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // TODO: Show some yhteenveto of course here, maybe par total yms
        val item = courseListClass[position]
        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, item)
            true
        }
        holder.itemView.setOnLongClickListener { view ->
            onLongClickListener.invoke(view, item)
            true
        }
        holder.name.text = courseListClass[position].name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.name)
    }
}


