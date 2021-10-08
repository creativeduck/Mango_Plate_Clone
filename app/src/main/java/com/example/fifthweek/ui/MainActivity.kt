package com.example.fifthweek.ui

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.fifthweek.FragmentPagerAdapter
import com.example.fifthweek.R
import com.example.fifthweek.UserInfo
import com.example.fifthweek.databinding.ActivityMainBinding
import com.example.fifthweek.fragment.FragmentDiscovery
import com.example.fifthweek.fragment.FragmentMy
import com.example.fifthweek.fragment.FragmentNews
import com.example.fifthweek.fragment.FragmentSale
import com.facebook.AccessToken
import com.kakao.sdk.user.UserApiClient
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.widget.LocationButtonView
import com.ssacproject.fourthweek.room.RoomUserDao
import com.ssacproject.fourthweek.room.RoomUserDatabase
import com.ssacproject.fourthweek.room.RoomUserInfo




class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1100
        const val REQUEST_PERMISSIONS = 12
        const val PERMISSIONS = 13
    }

    lateinit var binding : ActivityMainBinding
    var fragmentList = listOf(FragmentDiscovery(), FragmentSale(), FragmentNews(), FragmentMy())
    lateinit var fragmentPagerAdapter: FragmentStateAdapter
    lateinit var mapFragment : MapFragment
    lateinit var cameraUpdate : CameraUpdate
    lateinit var mNaverMap: NaverMap
    lateinit var mLocationSource : FusedLocationSource
    lateinit var locationBtn : LocationButtonView

    var menu : Menu? = null
    val TAG = "KAKAO_LOGIN"
    var mapOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val permissions = arrayOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        checkPermissions(permissions)


        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar

        val fm = supportFragmentManager
        mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
        }
        mapFragment.getMapAsync(this)


        fragmentPagerAdapter = FragmentPagerAdapter(this, fragmentList)

        binding.bottomnavi.run {
            setOnNavigationItemSelectedListener {
                val page : Int = when (it.itemId) {
                    R.id.discover -> 0
                    R.id.sale -> 1
                    R.id.news -> 2
                    else -> 3
                }

                if (page != binding.mainViewpager.currentItem) {
                    binding.mainViewpager.currentItem = page

                }

                true
            }
            selectedItemId = R.id.discover
        }

        binding.mainViewpager.run {
            adapter = fragmentPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val navigation = when(position) {
                        0 -> R.id.discover
                        1 -> R.id.sale
                        2 -> R.id.news
                        else -> R.id.my
                    }
                    if (binding.bottomnavi.selectedItemId != navigation)
                        binding.bottomnavi.selectedItemId = navigation
                }
            })
        }

//        UserApiClient.instance.me { user, error ->
//            if (error != null) {
//                Log.e(TAG, "사용자 정보 요청 실패", error)
//            }
//            else if (user != null) {
//                var scopes = mutableListOf<String>()
//                if (user.kakaoAccount?.emailNeedsAgreement == true) { scopes.add("account_email") }
//                if (user.kakaoAccount?.birthdayNeedsAgreement == true) { scopes.add("birthday") }
//                if (user.kakaoAccount?.birthyearNeedsAgreement == true) { scopes.add("birthyear") }
//                if (user.kakaoAccount?.genderNeedsAgreement == true) { scopes.add("gender") }
//                if (user.kakaoAccount?.phoneNumberNeedsAgreement == true) { scopes.add("phone_number") }
//                if (user.kakaoAccount?.profileNeedsAgreement == true) { scopes.add("profile") }
//                if (user.kakaoAccount?.ageRangeNeedsAgreement == true) { scopes.add("age_range") }
//                if (user.kakaoAccount?.ciNeedsAgreement == true) { scopes.add("account_ci") }
//
//                if (scopes.count() > 0) {
//                    Log.d(TAG, "사용자에게 추가 동의를 받아야 합니다.")
//
//                    UserApiClient.instance.loginWithNewScopes(this, scopes) { token, error ->
//                        if (error != null) {
//                            Log.e(TAG, "사용자 추가 동의 실패", error)
//                        } else {
//                            Log.d(TAG, "allowed scopes : ${token!!.scopes}")
//
//                            UserApiClient.instance.me { user, error ->
//                                if (error != null) {
//                                    Log.e(TAG, "사용자 정보 요청 실패", error)
//                                } else if (user != null) {
//                                    Log.i(TAG, "사용자 정보 요청 성공")
//                                }
//                            }
//                        }
//                    }
//
//                }
//
//            }
//        }



        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                val id = user.id
                setSharedData("userId", "userId", id)
                val nickname = user.kakaoAccount?.profile?.nickname
                setSharedData("userNickName", "userNickName", nickname)
                val profile = user.kakaoAccount?.profile?.thumbnailImageUrl
                setSharedData("userProfile", "userProfile", profile)
//                val str = "id = ${id} " + "\n" +
//                        "nickname = ${nickname}" + "\n" +
//                        "profile = ${profile}" + "\n"
//                Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
//                Log.d(TAG, "사용자 정보 성공적")

            }
        }

        val email = getSharedStringData("faceEmail", "faceEmail")
        val name = getSharedStringData("faceName", "faceName")
        val pic = getSharedStringData("facePic", "facePic")
        if (email != null)
            showToast(email)
        if (name != null)
            showToast(name)
        if (pic != null) {
            showToast(pic)
        }
        cameraUpdate = CameraUpdate.scrollTo(LatLng(37.5666102, 126.9783881))
            .animate(CameraAnimation.Linear)
        mLocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        locationBtn = binding.locationBtn



    }
    fun setSharedData(name: String, key: String, data: Long) {
        val pref = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putLong(key, data)
        editor.apply()
    }
    fun setSharedData(name: String, key: String, data: String?) {
        val pref = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, data)
        editor.apply()
    }
    fun getSharedLongData(name: String, key: String): Long {
        val pref = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getLong(key, 0)
    }
    fun getSharedStringData(name: String, key: String): String? {
        val pref = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getString(key, null)
    }


    override fun onStart() {
        super.onStart()
        Log.d("LIFE", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LIFE", "onResume()")
    }

    fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        getMenuInflater().inflate(R.menu.app_bar_menu, this.menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                if (mNaverMap != null) {
                    mNaverMap.moveCamera(cameraUpdate)
                }

            }
            R.id.map -> {
                if (mapOpened) {
                    binding.linearFragment.visibility = View.GONE
                    if (menu != null) {
                        menu!!.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_outline_map_24))
                    }
                    mapOpened = false
                }
                else {
                    binding.linearFragment.visibility = View.VISIBLE
                    menu!!.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_search_shape_white))
                    mapOpened = true
                }
            }
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(@NonNull naverMap: NaverMap) {
        val marker = Marker()
        mNaverMap = naverMap
        mNaverMap.locationSource = mLocationSource
        mNaverMap.locationTrackingMode = LocationTrackingMode.Follow
        locationBtn.map = mNaverMap
        marker.position = LatLng(37.234123, 126.2342)
        marker.map = naverMap



    }
    private fun checkPermissions(permissions: Array<String>) {
        val requestList: ArrayList<String> = ArrayList()
        for (i in permissions.indices) {
            val curPermission = permissions[i]
            val permissionCheck = ContextCompat.checkSelfPermission(this, curPermission)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, curPermission)) {
                    Toast.makeText(
                        applicationContext,
                        "$curPermission 권한 설명 필요",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    requestList.add(curPermission)
                }
            }
        }
        if (requestList.count() > 0) {
            val requests: Array<String> = requestList.toArray(arrayOfNulls(requestList.count()))
            ActivityCompat.requestPermissions(this, requests, REQUEST_PERMISSIONS)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (mLocationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!mLocationSource.isActivated) { // 권한 거부됨
                mNaverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }


        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.size > 0) {
                for (i in grantResults.indices) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(
                            this, "${i}번째 권한 허용됨", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this, "${i}번째 권한 거부됨" ,Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}