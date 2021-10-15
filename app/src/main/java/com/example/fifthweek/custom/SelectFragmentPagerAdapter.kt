package com.example.fifthweek.custom

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.example.fifthweek.OnItemClickListener
import com.example.fifthweek.fragment.SelectFragment

class SelectFragmentPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    var fragmentList = ArrayList<FragName>()
    var listenr : OnItemClickListener? = null

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].frag
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.setOnClickListener {
            if (listenr != null) {
                listenr!!.onItemClicked(holder.itemView, position)
            }
        }
    }


}

data class FragName(
    val frag: SelectFragment,
    val name: String
)