package nks.griplockiot.startgame

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class GamePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return GameFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        return 36
    }
}