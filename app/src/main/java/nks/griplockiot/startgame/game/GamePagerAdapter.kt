package nks.griplockiot.startgame.game

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class GamePagerAdapter(fragmentManager: FragmentManager, private val holes: Int) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return GameFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        return holes
    }
}