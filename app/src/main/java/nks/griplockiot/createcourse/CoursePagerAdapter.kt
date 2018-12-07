package nks.griplockiot.createcourse

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.coroutines.ObsoleteCoroutinesApi

class CoursePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    @ObsoleteCoroutinesApi
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CreateCourseFragment()
            1 -> ViewCourseFragment()
            else -> CreateCourseFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Create course"
            1 -> "View courses"
            else -> "Create course"
        }
    }

    override fun getCount(): Int {
        return 2
    }
}