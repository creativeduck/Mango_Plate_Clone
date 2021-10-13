package com.example.fifthweek.custom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPagerAdapter(fragmentActivity: FragmentActivity, val fragmentList: List<Fragment>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}