package nks.griplockiot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import nks.griplockiot.R
import nks.griplockiot.data.CourseAdapterLiveData.ViewHolder
import nks.griplockiot.model.Course

class CourseAdapterLiveData : RecyclerView.Adapter<ViewHolder>() {

    private var courseList: List<Course> = ArrayList()
    private lateinit var listenerClass: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    fun setNotes(courses: List<Course>) {
        courseList = courses
        notifyDataSetChanged()
    }

    fun getCourseAt(pos: Int): Course {
        return courseList[pos]
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = courseList[position]
        holder.name?.text = item.name
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView?>(R.id.name)

        init {
            itemView.setOnClickListener {
                listenerClass.onItemClick(courseList[adapterPosition])
                listenerClass.deleteCourseAtPos(adapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(course: Course)
        fun deleteCourseAtPos(pos: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        listenerClass = listener
    }
}


