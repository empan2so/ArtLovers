package com.example.artlovers.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.artlovers.R
import com.example.artlovers.ui.feeds.home.HomeFeedFragment
import com.example.artlovers.ui.feeds.loved.LovedFeedFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/*
 * Fragment uses TabLayout + ViewPager to display HomeFeedFragment and LovedFeedFragment
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // view pager
        val viewPager: ViewPager2 = view.findViewById(R.id.pager)
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = ViewPagerAdapter(requireActivity())

        // tab layout
        val tabLayout: TabLayout = view.findViewById(R.id.tabs)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_home)
                else -> getString(R.string.tab_loved)
            }
        }.attach()
    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFeedFragment()
            else -> LovedFeedFragment()
        }
    }
}
