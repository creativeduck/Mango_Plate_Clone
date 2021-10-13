package com.example.fifthweek.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.location.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.fifthweek.*
import com.example.fifthweek.custom.FragmentPagerAdapter
import com.example.fifthweek.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.kakao.sdk.user.UserApiClient
//import com.naver.maps.map.*
import java.lang.Exception

import java.util.*
import kotlin.collections.ArrayList
import com.google.android.gms.maps.CameraUpdateFactory
import com.example.fifthweek.R
import com.example.fifthweek.tastey_data.*
import noman.googleplaces.PlacesException
import noman.googleplaces.PlacesListener
//import noman.googleplaces.Place
import noman.googleplaces.PlaceType

import noman.googleplaces.NRPlaces
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.OnSuccessListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.IllegalArgumentException
import android.text.style.UnderlineSpan

import android.text.SpannableString
import android.util.DisplayMetrics
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fifthweek.kakao_image.*
import androidx.room.Room
import com.example.fifthweek.custom.FragName
import com.example.fifthweek.custom.MarkerItem
import com.example.fifthweek.custom.SelectFragmentPagerAdapter
import com.example.fifthweek.fragment.*
import com.example.fifthweek.near.NearPlace
import com.example.fifthweek.near.NearPlaceOpenApi
import com.example.fifthweek.near.NearPlaceOpenService
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_ENPYEONG
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_SEODAMUN
import com.example.fifthweek.room.GuItem
import com.example.fifthweek.seoul.Row
import com.example.fifthweek.seoul.Seoul
import com.example.fifthweek.seoul.SeoulOpenApi
import com.example.fifthweek.seoul.SeoulOpenService
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_DOBONG
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_DONDAEMUN
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_DONGJACK
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_GUMCHEON
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_GURO
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_GWANACK
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_GWANGJIN
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_JONGNO
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_JUNG
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_JUNGRAN
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_KANGBUK
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_KANGDONG
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_KANGNAM
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_KANGSEO
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_MAPO
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_NOONE
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_SEOCHOE
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_SEONGBUK
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_SEONGDONG
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_SONGPA
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_YANGCHEON
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_YEONGDEONG
import com.example.fifthweek.ui.SelectLocationActivity.Companion.RESULT_YONGSAN
import com.ssacproject.fourthweek.room.RoomFoodDatabase
import com.ssacproject.fourthweek.room.RoomGuItemDatabase
import com.ssacproject.fourthweek.room.RoomSeoulDatabase
import kotlinx.coroutines.selects.select
import kotlin.math.pow
import kotlin.math.sqrt


class MainActivity : AppCompatActivity(), OnMapReadyCallback, PlacesListener,
                    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
                    LocationListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {
    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1100
        const val REQUEST_PERMISSIONS = 12
        const val PERMISSIONS = 13
        const val TAG = "KAKAO_LOGIN"
        const val M_MAX_ENTRIES = 10
        const val DISTANCE_100 = 100
        const val DISTANCE_300 = 300
        const val DISTANCE_500 = 500
        const val DISTANCE_1k = 1000
        const val DISTANCE_3k = 3000
        const val REQUEST_LOCATION = 11
        var roomFoodDatabse : RoomFoodDatabase? = null
        var roomSeoulDatabase : RoomSeoulDatabase? = null
        var roomGuItemDatabase : RoomGuItemDatabase? = null
    }

    lateinit var binding : ActivityMainBinding
    var fragmentList = listOf(FragmentDiscovery(), FragmentSale(), FragmentNews(), FragmentMy())
    lateinit var fragmentPagerAdapter: FragmentStateAdapter
    lateinit var mGoogleMap : GoogleMap
    lateinit var mapFragment : SupportMapFragment
    var location : LatLng? = null
    lateinit var mPlacesClient : PlacesClient
    lateinit var mFusedLocationProviderClient: FusedLocationProviderClient
    lateinit var currentPosition : LatLng
    lateinit var bottomFrag : BottomSheetFragment
    lateinit var bottomFragTastey : BottomSheetFragmentTastey

    lateinit var googleApiClient : GoogleApiClient
    lateinit var selectListAdapter : SelectItemListAdapter
    lateinit var selectFragmentAdapter : SelectFragmentPagerAdapter

    lateinit var markerText : TextView
    lateinit var markerRoot : View
    var selectedMarker : Marker? = null
    var markerItemList = ArrayList<MarkerItem>()
    var markerList = ArrayList<Marker>()
    var selectedMarkerIndex = 0

    var distance = 100
    var circle : Circle? = null
    var previous_marker: MutableList<Marker>? = null
    var menu : Menu? = null
    var mapOpened = false
    var flag = false
    val nearList = ArrayList<GuItem>()
    val selectFragmentList = ArrayList<FragName>()
    var thisIsMarkerTime = false
    var changeDis = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomFrag = BottomSheetFragment()
        bottomFragTastey = BottomSheetFragmentTastey()

        val permissions = arrayOf<String>(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION)
        checkPermissions(permissions)

        startRoomDatabase()
        previous_marker = mutableListOf<Marker>()

        val apikey = BuildConfig.GOOGLE_MAP_API_KEY
        Places.initialize(applicationContext, apikey)
        mPlacesClient = Places.createClient(this)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayShowTitleEnabled(false)
        val mystring = binding.toolbarTitle.text.toString()
        val content = SpannableString(mystring)
        content.setSpan(UnderlineSpan(), 0, mystring.length, 0)
        binding.toolbarTitle.text = content


        try {
            MapsInitializer.initialize(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val fm = supportFragmentManager
        mapFragment = fm.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        location = getMyLocation()
        if (location != null) {
            currentPosition = location!!
        }

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

        binding.controlDistanceLayout.apply {
            btnDistance.setOnClickListener {
                bottomFrag.show(supportFragmentManager, bottomFrag.tag)
            }
            btnFilter.setOnClickListener {
                bottomFragTastey.show(supportFragmentManager, bottomFragTastey.tag)
            }
        }

        binding.toolbarTitle.setOnClickListener {
            val pos = binding.mainViewpager.currentItem
            if (pos == 0) {
                val intent = Intent(this, SelectLocationActivity::class.java)
                val name = binding.toolbarTitle.text.toString()
                intent.putExtra("name", name)
                startActivityForResult(intent, REQUEST_LOCATION)
            }
        }

        selectFragmentAdapter = SelectFragmentPagerAdapter(this)
        selectFragmentAdapter.fragmentList = selectFragmentList
        selectFragmentAdapter.listenr = object : OnItemClickListener {
            override fun onItemClicked(view: View, pos: Int) {
                val pos = binding.selectItemViewPager.currentItem
                val curFragName = selectFragmentList[pos]
                val name = curFragName.name
                val guItem = roomGuItemDatabase?.roomGuItemDao?.getItem(name)
                val intent = Intent(applicationContext, SpecificActivity::class.java)
                intent.putExtra("parcelItem", guItem)
                startActivity(intent)
            }
        }
        binding.selectItemViewPager.apply {
            adapter = selectFragmentAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    val mark = nearList[position]
                    val loca = LatLng(mark.Lng, mark.Lat)
                    mGoogleMap.animateCamera(
                        CameraUpdateFactory.newLatLng(loca),
                        800, null
                    )
                    if (!thisIsMarkerTime) {
                        val marker = markerList[position]
                        selectMarker(marker)
                    } else {
                        thisIsMarkerTime = false
                    }
                }
            })
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.right = 60
                    outRect.left = 60
                }
            })
            offscreenPageLimit = 1
            setPageTransformer { page, position ->
                page.translationX = -90 * position
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        mFusedLocationProviderClient.lastLocation
            .addOnSuccessListener(this, object : OnSuccessListener<Location> {
                override fun onSuccess(tmpLocation: Location?) {
                    if (tmpLocation != null) {
                        location = LatLng(tmpLocation.latitude, tmpLocation.longitude)
                        if (mGoogleMap != null) {
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                        }
                        currentPosition = location!!

                        if (!flag) {
                            val geocoder = Geocoder(applicationContext, Locale.getDefault())
                            val address = geocoder.getFromLocation(currentPosition.latitude, currentPosition.longitude, 1)
                            val name = address[0].subLocality
                            setTitle(name)
                            val frag = fragmentList[0] as FragmentDiscovery
                            frag.showGu(name)
                            flag = true
                        }
                    } else {

                    }
                }
            })
    }

    fun changeDistance(pos : Int) {
        distance = when (pos) {
            0 -> DISTANCE_100
            1 -> DISTANCE_300
            2 -> DISTANCE_500
            3 -> DISTANCE_1k
            else -> DISTANCE_3k
        }
        var distanceStr = ""
        if (distance < 1000) {
            distanceStr = "${distance}m"
        } else {
            distanceStr = "${distance/1000}km"
        }
        binding.controlDistanceLayout.btnDistance.text = distanceStr
        changeDis = true
        selectMarker(null)
        drawNearPlace()
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
    fun getSharedStringData(name: String, key: String): String? {
        val pref = getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getString(key, null)
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
                    menu!!.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_back_shape))
                    mapOpened = true
                }
            }
            else -> return true
        }
        return super.onOptionsItemSelected(item)
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
    // 구글맵 사용시
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        mGoogleMap.setOnMarkerClickListener(this)
        mGoogleMap.setOnMapClickListener(this)
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        googleApiClient.connect()

        val SEOUL = LatLng(37.5960451, 126.9332033)
        // 만약 위치가 없다면 여기로 일단 설정
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SEOUL, 15f))
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mGoogleMap.isMyLocationEnabled = true
        mGoogleMap.clear() //지도 클리어
        if (previous_marker != null) previous_marker!!.clear()

        val location = LatLng(SEOUL.latitude, SEOUL.longitude)
        val markerOptions = MarkerOptions()
        markerOptions.position(location)
        markerOptions.title("서울")
        markerOptions.icon(BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        val item: Marker = mGoogleMap.addMarker(markerOptions)
        previous_marker!!.add(item)

        drawCircle(distance)
        markerRoot = LayoutInflater.from(this).inflate(R.layout.custom_marker, null)
        markerText = markerRoot.findViewById(R.id.markertext)
        drawNearPlace()

    }

    fun getMyLocation() : LatLng? {
        val locationProvider = LocationManager.GPS_PROVIDER
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }
        val lastKnownLocation : Location = locationManager.getLastKnownLocation(locationProvider)!!
        return LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
    }

    override fun onPlacesFailure(e: PlacesException?) {
    }

    override fun onPlacesStart() {
    }

    override fun onPlacesSuccess(places: MutableList<noman.googleplaces.Place>) {
        runOnUiThread {
            for (place in places) {
                val latLng = LatLng(
                    place.latitude, place.longitude
                )
                val markerSnippet: String = getCurrentAddress(latLng)
                val markerOptions = MarkerOptions()
                markerOptions.position(latLng)
                markerOptions.title(place.name)
                markerOptions.snippet(markerSnippet)
                markerOptions.icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                val item: Marker = mGoogleMap.addMarker(markerOptions)
                previous_marker!!.add(item)
            }
            removeOverlapMarker()
            drawCircle(distance)
        }
    }
    override fun onPlacesFinished() {

    }

    fun showPlaceInformation(location: LatLng) {
        mGoogleMap.clear() //지도 클리어
        if (previous_marker != null) previous_marker!!.clear() //지역정보 마커 클리어
        NRPlaces.Builder()
            .listener(this@MainActivity)
            .key(BuildConfig.GOOGLE_PLACES_API_KEY)
            .latlng(37.59602827773711, 126.9333567162235)
            .radius(distance) //500 미터 내에서 검색
            .type(PlaceType.RESTAURANT) //음식점
            .language("ko", "KR")
            .build()
            .execute()
    }

    fun getCurrentAddress(latlng: LatLng): String {
        //지오코더... GPS를 주소로 변환
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?
        addresses = try {
            geocoder.getFromLocation(
                latlng.latitude,
                latlng.longitude,
                1
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Log.e("GEOCODER", "지오코더 서비스 사용불가")
            return "지오코더 서비스 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Log.e("GEOCODER", "잘못된 GPS 좌표")
            return "잘못된 GPS 좌표"
        }
        return if (addresses == null || addresses.size == 0) {
            Log.e("GEOCODER", "주소 미발견")
            "주소 미발견"
        } else {
            val address: Address = addresses[0]
            address.getAddressLine(0).toString()
        }
    }

    override fun onConnected(bundle: Bundle?) {

    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onLocationChanged(p0: Location) {

    }

    fun removeCircle() {
        if (circle != null)
            circle!!.remove()
    }

    fun drawCircle(rad : Int) {
        if (circle != null) {
            circle!!.remove()
        }
        val circleOptions = CircleOptions()
        circleOptions.center(currentPosition)
        circleOptions.radius(rad.toDouble())
        circleOptions.strokeColor(Color.parseColor("#5184F0"))
        circleOptions.fillColor(0x305184F0)
        circleOptions.strokeWidth(3f)
        circle = mGoogleMap.addCircle(circleOptions)
    }
    fun loadNearPlace() {
        val retrofit = Retrofit.Builder()
            .baseUrl(NearPlaceOpenApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NearPlaceOpenService::class.java)
        val key = BuildConfig.GOOGLE_PLACES_API_KEY
        val location = "${currentPosition.latitude},${currentPosition.longitude}"
        val radius = distance
        val language = "ko"
        val type = "restaurant"
        service.getNearPlace(key, location, radius, language, type)
            .enqueue(object : Callback<NearPlace> {
                override fun onResponse(call: Call<NearPlace>, response: Response<NearPlace>) {
                    val result = response.body()
                    if (result != null) {
                        getNearPlace(result)
                    }
                }
                override fun onFailure(call: Call<NearPlace>, t: Throwable) {
                    Log.e("NearPlace", "실패함")
                }
            })
    }
    fun getNearPlace(result : NearPlace) {
        val results = result.results
        for (place in results) {
            val name = place.name
            val gu = place.vicinity.split(" ")[0]
            val type = "음식"
            val menu = "메뉴"
            var tmpAddress = ""
            val addressList = place.plus_code.compound_code.split(" ")
            for (i in 1 until addressList.size) {
                tmpAddress += addressList[i] + " "
            }
            val address = tmpAddress + gu
            val tel = "010-1234-5678"
            val Lat = place.geometry.location.lat
            val Lng = place.geometry.location.lng
            getImage(type, gu, menu, address, tel,name, Lng, Lat)
        }
    }

    fun loadTastey(size : Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(TasteyOpenApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TasteyOpenService::class.java)
        val key = BuildConfig.GYEONGGI_API_KEY
        service.getTastey(key, size)
            .enqueue(object : Callback<Tastey> {
                override fun onResponse(
                    call: Call<Tastey>,
                    response: Response<Tastey>
                ) {
                    val result = response.body()
                    showTastey(result)
                }
                override fun onFailure(call: Call<Tastey>, t: Throwable) {
                    Log.e("TASTEY", "데이터 못 가져옴")
                }
            })

    }
    fun loadParagon(size : Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(TasteyOpenApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ParagonOpenService::class.java)
        val key = BuildConfig.GYEONGGI_API_KEY
        service.getParagon(key, size)
            .enqueue(object : Callback<Paragon> {
                override fun onResponse(
                    call: Call<Paragon>,
                    response: Response<Paragon>
                ) {
                    val result = response.body()
                    showParagon(result)
                }
                override fun onFailure(call: Call<Paragon>, t: Throwable) {
                    Log.e("TASTEY", "데이터 못 가져옴")
                }
            })

    }
    fun showParagon(result: Paragon?) {
        result?.let {
            val place = it.ParagonRestaurant[1]
            val row = place.row
            runOnUiThread {
                mGoogleMap.clear() //지도 클리어
                if (previous_marker != null) previous_marker!!.clear()
                for (r in row) {
                    val location = LatLng(r.REFINE_WGS84_LAT.toDouble(), r.REFINE_WGS84_LOGT.toDouble())
                    addLocationMarker(r.BIZESTBL_NM, r.REFINE_LOTNO_ADDR, location)
                }
                removeOverlapMarker()
                drawCircle(distance)
            }
        }
    }
    fun showTastey(result: Tastey?) {
        result?.let {
            val place = it.PlaceThatDoATasteyFoodSt[1]
            val row = place.row
            runOnUiThread {
                mGoogleMap.clear() //지도 클리어
                if (previous_marker != null) previous_marker!!.clear()

                for (r in row) {
                    val location = LatLng(r.REFINE_WGS84_LAT.toDouble(), r.REFINE_WGS84_LOGT.toDouble())
                    val markerSnippet: String = getCurrentAddress(location)
                    addLocationMarker(r.RESTRT_NM, r.REFINE_ROADNM_ADDR, location)
                }
                removeOverlapMarker()
                drawCircle(distance)
            }
        }
    }
    fun loadSafe(size : Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(TasteyOpenApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SafeOpenService::class.java)
        val key = BuildConfig.GYEONGGI_API_KEY
        service.getSafe(key, size)
            .enqueue(object : Callback<Safe> {
                override fun onResponse(
                    call: Call<Safe>,
                    response: Response<Safe>
                ) {
                    val result = response.body()
                    showSafe(result)

                }
                override fun onFailure(call: Call<Safe>, t: Throwable) {
                    Log.e("TASTEY", "데이터 못 가져옴")
                }
            })

    }
    fun showSafe(result: Safe?) {
        result?.let {
            val place = it.SafetyRestrntInfo[1]
            val row = place.row
            runOnUiThread {
                if (circle != null) {
                    removeCircle()
                }
                mGoogleMap.clear() //지도 클리어
                if (previous_marker != null) previous_marker!!.clear()
                for (r in row) {
                    val lat = r.REFINE_WGS84_LAT
                    val lng = r.REFINE_WGS84_LOGT
                    if (lat != null && lng != null) {
                        val location = LatLng(r.REFINE_WGS84_LAT.toDouble(), r.REFINE_WGS84_LOGT.toDouble())
                        addLocationMarker(r.BIZPLC_NM, r.DETAIL_ADDR, location)
                    }
                }
                removeOverlapMarker()
                drawCircle(distance)
            }
        }
    }
    private fun getAddress(code: Int) : String {
        return when (code) {
            RESULT_ENPYEONG -> "은평구"
            RESULT_SEODAMUN -> "서대문구"
            RESULT_JUNGRAN -> "중랑구"
            RESULT_JUNG -> "중구"
            RESULT_JONGNO -> "종로구"
            RESULT_YONGSAN -> "용산구"
            RESULT_YEONGDEONG -> "영등포구"
            RESULT_YANGCHEON -> "양천구"
            RESULT_SONGPA -> "송파구"
            RESULT_SEONGBUK -> "성북구"
            RESULT_SEONGDONG -> "성동구"
            RESULT_SEOCHOE -> "서초구"
            RESULT_MAPO -> "마포구"
            RESULT_DONGJACK -> "동작구"
            RESULT_DONDAEMUN -> "동대문구"
            RESULT_DOBONG -> "도봉구"
            RESULT_NOONE -> "노원구"
            RESULT_GUMCHEON -> "금천구"
            RESULT_GURO -> "구로구"
            RESULT_GWANGJIN -> "광진구"
            RESULT_GWANACK -> "관악구"
            RESULT_KANGSEO -> "강서구"
            RESULT_KANGBUK -> "강북구"
            RESULT_KANGDONG -> "강동구"
            RESULT_KANGNAM -> "강남구"
            else -> ""
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_LOCATION) {
            val frag = fragmentList[0] as FragmentDiscovery
            var name = getAddress(resultCode)
            if (name.length > 0) {
                setTitle(name)
                frag.showGu(name)
            }
        }
    }
    fun setTitle(name: String) {
        val content = SpannableString(name)
        content.setSpan(UnderlineSpan(), 0, name.length, 0)
        binding.toolbarTitle.text = content
    }

    fun loadSeoul() {
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SeoulOpenService::class.java)
        val key = BuildConfig.SEOUL_API_KEY
        service.getSeoul(key,1, 1000)
            .enqueue(object : Callback<Seoul> {
                override fun onResponse(
                    call: Call<Seoul>,
                    response: Response<Seoul>
                ) {
                    val result = response.body()
                    showSeoul(result)
                }
                override fun onFailure(call: Call<Seoul>, t: Throwable) {
                    Log.e("TASTEY", "데이터 못 가져옴")
                }
            })
    }

    fun showSeoul(result: Seoul?) {
        result?.let {
            val places = it.CrtfcUpsoInfo
            val row = places.row
            if (row != null) {
                for (r in row) {
                    // room 사용해서 데이터 넣기
                    val item = Row(r.BIZCND_CODE, r.BIZCND_CODE_NM, r.CGG_CODE, r.CGG_CODE_NM,
                        r.COB_CODE, r.COB_CODE_NM, r.CRTFC_CHR_ID, r.CRTFC_CHR_NM, r.CRTFC_CLASS,
                        r.CRTFC_GBN, r.CRTFC_GBN_NM, r.CRTFC_SNO, r.CRTFC_UPSO_MGT_SNO, r.CRTFC_YMD,
                        r.CRTFC_YN, r.CRT_TIME, r.CRT_USR, r.FOOD_MENU, r.GNT_NO, r.MAP_INDICT_YN,
                        r.OWNER_NM, r.RDN_ADDR_CODE, r.RDN_CODE_NM, r.RDN_DETAIL_ADDR, r.TEL_NO,
                        r.UPD_TIME, r.UPSO_NM, r.UPSO_SNO, r.USE_YN, r.X_CNTS, r.Y_DNTS)
                    roomSeoulDatabase?.roomSeoulDao?.insert(item)
                }
            }
        }
    }
    fun setImage() {
        val newList = roomSeoulDatabase?.roomSeoulDao?.getAll()
        if (newList != null) {
            for (row in newList) {
                val foodName = row.BIZCND_CODE_NM
                val gu = row.CGG_CODE_NM
                val menu = row.FOOD_MENU
                val address = row.RDN_CODE_NM
                val tel = row.TEL_NO
                val name = row.UPSO_NM
                val Lat = row.X_CNTS.toDouble()
                val Lng = row.Y_DNTS.toDouble()
                getImage(foodName, gu, menu, address, tel, name, Lat, Lng)
            }
        }
    }
    fun getImage(foodName: String, gu: String, menu: String, address: String, tel: String,
                 name: String, Lat: Double, Lng: Double){
        val imageList = ArrayList<String>()
        SearchRetrofit.getService().requestSearchImage(keyword = name,
            sort = "accuracy", page = 1, size = 5)
            .enqueue(object : Callback<Image> {
                override fun onResponse(call: Call<Image>, response: Response<Image>) {
                    if (response.isSuccessful) {
                        val image = response.body()
                        val result = image?.documents
                        if (result != null && result.size > 0) {
                            // 이미지 모두 리스트에 저장
                            for (i in 0 until result.size) {
                                imageList.add(result[i].image_url)
                            }
                            val item = GuItem(name, gu, foodName, menu, address, tel, Lat, Lng, imageList)
                            roomGuItemDatabase?.roomGuItemDao?.insert(item)
                        }
                    }
                }
                override fun onFailure(call: Call<Image>, t: Throwable) {
                }
            })
    }
    fun drawNearPlace() {
        val list = roomGuItemDatabase?.roomGuItemDao?.getAll() as List<GuItem>
        nearList.clear()
        selectFragmentList.clear()
        markerList.clear()
        for (place in list) {
            val lat = place.Lng
            val lng = place.Lat
            var curLat = currentPosition.latitude
            var curLng = currentPosition.longitude
            if (calRadius(curLat, curLng, lat, lng, distance)) {
                nearList.add(place)
            }
        }
        mGoogleMap.clear() //지도 클리어
        if (previous_marker != null) previous_marker!!.clear()

        for (i in 0 until nearList.size) {
            val near = nearList[i]

            markerText.text = "${i+1}"
            val location = LatLng(near.Lng, near.Lat)
            val markerOptions = MarkerOptions()
            markerOptions.position(location)
            markerOptions.title("${i+1}")
            markerOptions.icon(BitmapDescriptorFactory
                .fromBitmap(createDrawableFromView(markerRoot)))
            val item: Marker = mGoogleMap.addMarker(markerOptions)
            previous_marker!!.add(item)
            markerList.add(item)
            addFragment(i, near)
        }
        selectFragmentAdapter.fragmentList = selectFragmentList
        selectFragmentAdapter.notifyDataSetChanged()
        removeOverlapMarker()
        drawCircle(distance)
        selectMarker(markerList[0])
        binding.selectItemViewPager.currentItem = 0
    }
    fun calRadius(curLat: Double, curLng: Double, Lat: Double, Lng: Double, radius: Int): Boolean {
        val R = 6373.0
        val dlon = curLng - Lng
        val dlat = curLat - Lat
        val a = (Math.sin(dlat/2)).pow(2) + Math.cos(Lat) * Math.cos(curLat) * (Math.sin(dlon/2)).pow(2)
        val c = 2 * Math.atan2(sqrt(a), sqrt(1-a))
        val dis = R * c * 17
        return dis <= radius.toDouble()
    }

    private fun createDrawableFromView(view: View) : Bitmap {

        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val center = CameraUpdateFactory.newLatLng(marker.position)
        mGoogleMap.animateCamera(center)

        selectMarker(marker)
        if (marker.title != null)  {
            val pos = marker.title.toInt()
            thisIsMarkerTime = true
            binding.selectItemViewPager.currentItem = pos - 1
        }
        binding.selectItemViewPager.visibility = View.VISIBLE
        return true
    }
    private fun selectMarker(marker: Marker?) {
        if (selectedMarker != marker) {
            if (selectedMarker != null) {
                var pos = markerList.indexOf(selectedMarker)

                val markerOptions = MarkerOptions()
                markerOptions.position(selectedMarker!!.position)
                markerOptions.title(selectedMarker!!.title)
                markerText.setBackgroundResource(R.drawable.ic_marker_unselected)
                markerText.text = selectedMarker!!.title
                markerText.setTextColor(getColor(R.color.marker_gray))
                markerOptions.icon(
                    BitmapDescriptorFactory
                        .fromBitmap(createDrawableFromView(markerRoot))
                )
                val item: Marker = mGoogleMap.addMarker(markerOptions)
                selectedMarker = null

                markerList[pos] = item

            }
            if (marker != null) {
                var pos = markerList.indexOf(marker)
                if (pos < 0) {
                    pos = 0
                }
                val location = marker.position
                marker.remove()
                // 선택되었을 때는 이미지 변경해서 다시 추가
                markerText.text = marker.title
                markerText.setBackgroundResource(R.drawable.ic_marker_image)
                markerText.setTextColor(getColor(R.color.orange))
                val markerOptions = MarkerOptions()
                markerOptions.position(location)
                markerOptions.title(marker.title)
                markerOptions.icon(BitmapDescriptorFactory
                    .fromBitmap(createDrawableFromView(markerRoot)))
                val item: Marker = mGoogleMap.addMarker(markerOptions)
                selectedMarker = marker

                markerText.setBackgroundResource(R.drawable.ic_marker_unselected)
                markerText.text = selectedMarker!!.title
                markerText.setTextColor(getColor(R.color.marker_gray))

                markerList[pos] = marker
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
            }

        }
    }

    fun addFragment(pos: Int, near: GuItem) {
        val fragment = SelectFragment()
        var bundle = Bundle()
        bundle.putString("name", near.name)
        bundle.putString("gu", near.gu)
        bundle.putInt("pos", pos+1)
        var image = getString(R.string.tmp_image)
        if (near.imageList.size > 0) {
            image = near.imageList[0]
        }
        bundle.putString("image", image)
        fragment.arguments = bundle
        val item = FragName(fragment, near.name)
        selectFragmentList.add(item)
    }
    //중복 마커 제거
    fun removeOverlapMarker() {
        val hashSet = HashSet<Marker>()
        hashSet.addAll(previous_marker!!)
        previous_marker!!.clear()
        previous_marker!!.addAll(hashSet)
    }
    fun startRoomDatabase() {
        roomFoodDatabse = Room.databaseBuilder(this, RoomFoodDatabase::class.java, "room_food_enpyeong2")
            .allowMainThreadQueries()
            .build()

        roomSeoulDatabase = Room.databaseBuilder(this, RoomSeoulDatabase::class.java, "room_seoul_1")
            .allowMainThreadQueries()
            .build()

        roomGuItemDatabase = Room.databaseBuilder(this, RoomGuItemDatabase::class.java, "room_food_gu")
            .allowMainThreadQueries()
            .build()
    }
    private fun addLocationMarker(title: String, snippet: String, location: LatLng) {
        val markerOptions = MarkerOptions()
        markerOptions.position(location)
        markerOptions.title(title)
        markerOptions.snippet(snippet)
        markerOptions.icon(BitmapDescriptorFactory
            .defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
        val item: Marker = mGoogleMap.addMarker(markerOptions)
        previous_marker!!.add(item)
    }

    override fun onMapClick(latlng: LatLng) {
        selectMarker(null)
        binding.selectItemViewPager.visibility = View.INVISIBLE
    }
}


data class MAPnURL(
    val map : GoogleMap,
    val url : String
)
