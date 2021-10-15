package com.example.fifthweek.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fifthweek.R
import com.example.fifthweek.custom.ImageAdapter
import com.example.fifthweek.databinding.ActivitySpecificBinding
import com.example.fifthweek.room.GuItem
import com.google.android.material.appbar.AppBarLayout
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class SpecificActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1100
    }

    lateinit var binding : ActivitySpecificBinding
    lateinit var imageAdapter : ImageAdapter
    var scrollRange = -1
    var menu : Menu? = null
    lateinit var mapFragment : MapFragment
    lateinit var mLocationSource : FusedLocationSource
    lateinit var naverMap : NaverMap
    lateinit var location : com.naver.maps.geometry.LatLng
    var name = ""
    var lng = 0.0
    var lat = 0.0
    val permissions = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecificBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var item = intent.getParcelableExtra<GuItem>("parcelItem") as GuItem

        val imageList = item.imageList
        name = item.name
        val menuList = item.menu
        val address = item.address
        val tel = item.tel
        lng = item.Lat
        lat = item.Lng
        location = com.naver.maps.geometry.LatLng(lat, lng)
        setSupportActionBar(binding.specificToolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.itemName.text = name
        binding.toolbarTextView.text = name
        binding.specificAddress.text = address
        binding.specificAddressSub.text = address

        imageAdapter = ImageAdapter()
        imageAdapter.imageList = imageList

        binding.specificRecycler.apply {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.stickyScrollView.run {
            header = binding.stickLinear
            stickListener = { _ ->
            }
            freeListener = { _ ->
            }
        }

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                if (menu != null) {
                    menu!!.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_camera_shape_white))
                    menu!!.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_share_shape_white))
                }
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_shape_white)
                binding.specificToolbar.setBackgroundColor(getColor(R.color.orange))
            }
            else {
                if (menu != null) {
                    menu!!.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_camera_shape))
                    menu!!.getItem(1).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_share_shape))
                }
                binding.specificToolbar.setBackgroundColor(getColor(R.color.white))
                actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_shape_orange)
            }
        })

        binding.linearCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel))
            startActivity(intent)
        }

        mapFragment = supportFragmentManager.findFragmentById(R.id.naverMap) as MapFragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
        }
        mapFragment.getMapAsync(this)

//                if (mNaverMap != null) {
//                    mNaverMap.moveCamera(cameraUpdate)
//                }

        mLocationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        getMenuInflater().inflate(R.menu.specific_menu, this.menu)
        return true
    }

    fun showToast(str: String) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    override fun onMapReady(mMap: NaverMap) {
        this.naverMap = mMap
        naverMap.setOnMapClickListener { pointF, latLng ->
            val intent = Intent(applicationContext, MapActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("lat", lat)
            intent.putExtra("lng", lng)
            startActivity(intent)
        }
        naverMap.locationSource = mLocationSource
        naverMap.uiSettings.apply {
            isScaleBarEnabled = false
            isZoomControlEnabled = false
        }

        val cameraUpdate = CameraUpdate.scrollTo(location)
        naverMap.moveCamera(cameraUpdate)

        val marker = Marker()
        marker.position = location
        marker.icon = OverlayImage.fromResource(R.drawable.ic_specific_location_shape)
        marker.map = naverMap

        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE)
    }
}