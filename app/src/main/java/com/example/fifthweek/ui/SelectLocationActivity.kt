package com.example.fifthweek.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fifthweek.*
import com.example.fifthweek.custom.GuAdapter
import com.example.fifthweek.databinding.ActivitySelectLocationBinding

class SelectLocationActivity : AppCompatActivity() {
    lateinit var binding : ActivitySelectLocationBinding
    var menu : Menu? = null
    lateinit var guAdapter: GuAdapter

    companion object {
        const val RESULT_SEODAMUN = 1
        const val RESULT_ENPYEONG = 2
        const val RESULT_JUNGRAN = 3
        const val RESULT_JUNG = 4
        const val RESULT_JONGNO = 5
        const val RESULT_YONGSAN = 6
        const val RESULT_YANGCHEON = 7
        const val RESULT_SONGPA = 8
        const val RESULT_SEONGBUK = 9
        const val RESULT_SEONGDONG = 10
        const val RESULT_SEOCHOE = 11
        const val RESULT_MAPO = 12
        const val RESULT_DONGJACK = 13
        const val RESULT_DONDAEMUN = 14
        const val RESULT_DOBONG = 15
        const val RESULT_NOONE = 16
        const val RESULT_GUMCHEON = 17
        const val RESULT_GURO = 18
        const val RESULT_GWANGJIN = 19
        const val RESULT_GWANACK = 20
        const val RESULT_KANGSEO = 21
        const val RESULT_KANGBUK = 22
        const val RESULT_KANGDONG = 23
        const val RESULT_KANGNAM = 24
        const val RESULT_YEONGDEONG = 25
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.thistoolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(false)

        val name = intent.getStringExtra("name")

        val tmp = listOf(WhatGu("중랑구", false), WhatGu("중구", false),
            WhatGu("종로구", false), WhatGu("은평구", false),
            WhatGu("용산구",false), WhatGu("영등포구", false),
            WhatGu("양천구", false), WhatGu("송파구",false),
            WhatGu("성북구", false), WhatGu("성동구", false),
            WhatGu("서초구", false), WhatGu("서대문구",false),
            WhatGu("마포구", false), WhatGu("동작구", false),
            WhatGu("동대문구",false), WhatGu("도봉구", false),
            WhatGu("노원구", false), WhatGu("금천구",false),
            WhatGu("구로구",false), WhatGu("광진구",false),
            WhatGu("관악구",false), WhatGu("강서구",false),
            WhatGu("강북구",false), WhatGu("강동구",false),
            WhatGu("강남구", false))
        val guList = ArrayList<WhatGu>()
        guList.addAll(tmp)
        guAdapter = GuAdapter()
        guAdapter.guList = guList
        guAdapter.listener = object : OnItemClickListener {
            override fun onItemClicked(view: View, pos: Int) {
                val item = guList[pos]
                val code = getCode(item.guName)
                setResult(code)
                finish()
            }
        }

        binding.gridRecycler.let {
            it.adapter = guAdapter
            it.layoutManager = GridLayoutManager(this, 2)
            it.addItemDecoration(GridSpacingItemDecoration(2, 35, true))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        getMenuInflater().inflate(R.menu.select_menu, this.menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.back -> {
                setResult(RESULT_CANCELED)
                finish()
            }
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onStop() {
        super.onStop()
        setResult(RESULT_CANCELED)
    }

    fun getCode(name: String) : Int {
        return when (name) {
            "은평구" -> RESULT_ENPYEONG
            "서대문구" -> RESULT_SEODAMUN
            "중랑구" -> RESULT_JUNGRAN
            "중구" -> RESULT_JUNG
            "종로구" -> RESULT_JONGNO
            "용산구" -> RESULT_YONGSAN
            "영등포구" -> RESULT_YEONGDEONG
            "양천구" -> RESULT_YANGCHEON
            "송파구" -> RESULT_SONGPA
            "성북구" -> RESULT_SEONGBUK
            "성동구" -> RESULT_SEONGDONG
            "서초구" -> RESULT_SEOCHOE
            "마포구" -> RESULT_MAPO
            "동작구" -> RESULT_DONGJACK
            "동대문구" -> RESULT_DONDAEMUN
            "도봉구" -> RESULT_DOBONG
            "노원구" -> RESULT_NOONE
            "금천구" -> RESULT_GUMCHEON
            "구로구" -> RESULT_GURO
            "광진구" -> RESULT_GWANGJIN
            "관악구" -> RESULT_GWANACK
            "강서구" -> RESULT_KANGSEO
            "강북구" -> RESULT_KANGBUK
            "강동구" -> RESULT_KANGDONG
            "강남구" -> RESULT_KANGNAM
            else -> RESULT_CANCELED
        }
    }

}