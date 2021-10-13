package com.example.fifthweek.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.fifthweek.*
import com.example.fifthweek.custom.CustomAdAdapter
import com.example.fifthweek.kakao_image.Image
import com.example.fifthweek.kakao_image.SearchRetrofit
import com.example.fifthweek.room.MainFoodItem
import com.example.fifthweek.ui.MainActivity.Companion.roomFoodDatabse
import com.example.fifthweek.ui.MainActivity.Companion.roomGuItemDatabase
import com.example.fifthweek.ui.SpecificActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ssacproject.thirdweek.room.GuItemListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentDiscovery : Fragment() {
    lateinit var handler: Handler
    lateinit var handrun: Runnable
    lateinit var viewpager_ad: ViewPager2
    lateinit var tablayout_ad: TabLayout
    lateinit var discoveryRecycler: RecyclerView
    lateinit var guItemListAdapter: GuItemListAdapter
    var flag = false

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

        guItemListAdapter = GuItemListAdapter()
        guItemListAdapter.listener = object : OnItemClickListener {
            override fun onItemClicked(view: View, pos: Int) {
                val intent = Intent(requireActivity(), SpecificActivity::class.java)
                var parcelItem = guItemListAdapter.currentList[pos]
                intent.putExtra("parcelItem", parcelItem)
                startActivity(intent)
            }
        }

        discoveryRecycler = view.findViewById(R.id.discoveryRecycler)
        discoveryRecycler.apply {
            adapter = guItemListAdapter
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(GridSpacingItemDecoration(2, 35, true))
        }
    }
    fun showGu(gu: String) {
        val newList = roomGuItemDatabase?.roomGuItemDao?.getTitleList(gu)
        if (newList != null) {
            guItemListAdapter.submitList(newList)
        }
        flag = true
    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(handrun)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(handrun, 3000)
    }

    fun getImage(name: String, gu: String){
        var url: String? = null
        SearchRetrofit.getService().requestSearchImage(keyword = name,
            sort = "accuracy", page = 1, size = 5)
            .enqueue(object : Callback<Image> {
                override fun onResponse(call: Call<Image>, response: Response<Image>) {
                    if (response.isSuccessful) {
                        val image = response.body()
                        val result = image?.documents
                        if (result != null) {
                            url = result[0].image_url
                        }
                        if (url == null)
                            url = getString(R.string.tmp_image)
                        val item = MainFoodItem(name, gu, 900, 1230,
                            134, 4.5, url!!, true)
                        roomFoodDatabse?.roomFoodDao?.insert(item)
                    }
                }
                override fun onFailure(call: Call<Image>, t: Throwable) {
                }
            })
    }

}