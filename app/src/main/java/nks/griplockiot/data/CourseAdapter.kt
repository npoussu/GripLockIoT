package nks.griplockiot.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import nks.griplockiot.R
import nks.griplockiot.model.Course

/**
 * CourseAdapter: Holds the list of Course-objects
 */
class CourseAdapter(courseList: ArrayList<Course>,
                    private val onClickListener: (View, Course) -> Unit,
                    private val onLongClickListener: (View, Course) -> Unit) : RecyclerView.Adapter<CourseAdapter.ViewHolder>(), CoroutineScope {

    // Holder for the list coming from the constructor, alter on modifying the Course
    private var courseListClass: ArrayList<Course> = courseList

    @ObsoleteCoroutinesApi
    override
    val coroutineContext = newFixedThreadPoolContext(2, "bg")

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
        val item = courseListClass[position]
        holder.itemView.setOnClickListener { view ->
            onClickListener.invoke(view, item)
        }
        holder.itemView.setOnLongClickListener { view ->
            onLongClickListener.invoke(view, item)
            true

        }
        holder.name?.text = courseListClass[position].name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView?>(R.id.name)
    }
}


