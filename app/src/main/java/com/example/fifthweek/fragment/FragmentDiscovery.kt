package com.example.fifthweek.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.fifthweek.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.currentCoroutineContext

class FragmentDiscovery : Fragment() {
    lateinit var handler: Handler
    lateinit var handrun: Runnable
    lateinit var viewpager_ad: ViewPager2
    lateinit var tablayout_ad: TabLayout
    lateinit var discoveryRecycler: RecyclerView
    lateinit var customFoodItemAdapter: CustomFoodItemAdapter

    val imageList = listOf(
        R.drawable.ad1, R.drawable.ad2, R.drawable.ad3, R.drawable.ad4, R.drawable.ad5, R.drawable.ad6, R.drawable.ad7
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discovery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customAdAdapter = CustomAdAdapter()
        customAdAdapter.let {
            it.imageList = imageList
        }
        val len = customAdAdapter.imageList.size
        viewpager_ad = view.findViewById(R.id.viewpager_ad)
        viewpager_ad.adapter = customAdAdapter
        handler = Handler()
        handrun = Runnable {
            if (viewpager_ad.currentItem == len-1) {
                viewpager_ad.currentItem = 0
            } else {
                viewpager_ad.currentItem = viewpager_ad.currentItem + 1

            }
        }
        viewpager_ad.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(handrun)
                handler.postDelayed(handrun, 3000)
            }
        })
        tablayout_ad = view.findViewById(R.id.tablayout_ad)
        TabLayoutMediator(tablayout_ad, viewpager_ad) { tab, position -> }.attach()

        customFoodItemAdapter = CustomFoodItemAdapter()
        val foodList = ArrayList<MainFoodItem>()
        foodList.add(
            MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, true))
        foodList.add(MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, true))
        foodList.add(MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, false))
        foodList.add(MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, true))
        foodList.add(MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, false))
        foodList.add(MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, true))
        foodList.add(MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, true))
        foodList.add(MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, true))
        foodList.add(MainFoodItem(1, "어라우즈", "은평규", 900, 1230,
            134, 4.5, R.drawable.food_item_bossam, true))



        customFoodItemAdapter.foodList = foodList

        discoveryRecycler = view.findViewById(R.id.discoveryRecycler)
        discoveryRecycler.apply {
            adapter = customFoodItemAdapter
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, 35, true))
        }






    }
    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(handrun)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(handrun, 3000)
    }

}