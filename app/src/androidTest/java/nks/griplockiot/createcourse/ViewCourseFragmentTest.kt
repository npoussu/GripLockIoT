package nks.griplockiot.createcourse

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ViewCourseFragmentTest {

    @Rule
    @JvmField
    val mActivityRule = IntentsTestRule(CreateCourseActivity::class.java)

    @Test
    fun testClickRecyclerViewFirstIndexOpenNewActivity() {
        onView(withId(nks.griplockiot.R.id.course_list_create_course)).perform(swipeLeft())

        // If RecyclerView is empty, don't click, if not empty, click first item
        if (getRowCount() > 0) {
            onView(withId(nks.griplockiot.R.id.course_list_view_course)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        }
    }

    private fun getRowCount(): Int {
        val recyclerView = mActivityRule.activity.findViewById<RecyclerView>(nks.griplockiot.R.id.course_list_view_course)
        return recyclerView.adapter!!.itemCount
    }
}