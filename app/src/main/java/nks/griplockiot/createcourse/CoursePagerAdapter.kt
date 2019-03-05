package nks.griplockiot.createcourse

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * CoursePagerAdapter: PagerAdapter for paging the different Fragments in the Activity
 */
class CoursePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

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