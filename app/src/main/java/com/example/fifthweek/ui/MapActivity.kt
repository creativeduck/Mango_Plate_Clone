package com.example.fifthweek.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import com.example.fifthweek.R
import com.example.fifthweek.databinding.ActivityMapBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var binding: ActivityMapBinding

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1100
    }

    lateinit var mapFragment : MapFragment
    lateinit var mLocationSource : FusedLocationSource
    lateinit var naverMap : NaverMap
    lateinit var location : LatLng
    val permissions = arrayOf<String>(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar.setHomeAsUpIndicator(R.drawable.ic_left_arrow_shape)

        val name = intent.getStringExtra("name")
        val lat = intent.getDoubleExtra("lat", 0.0)
        val lng = intent.getDoubleExtra("lng", 0.0)
        location = LatLng(lat, lng)
        binding.toolbar.title = name

        mapFragment = supportFragmentManager.findFragmentById(R.id.bigNaverMap) as MapFragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance()
        }
        mapFragment.getMapAsync(this)

        mLocationSource = FusedLocationSource(this,
            SpecificActivity.LOCATION_PERMISSION_REQUEST_CODE
        )




    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SpecificActivity.LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                naverMap.locationTrackingMode = LocationTrackingMode.NoFollow
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


    override fun onMapReady(mMap: NaverMap) {
        this.naverMap = mMap
        naverMap.locationSource = mLocationSource
        naverMap.uiSettings.apply {
            isScaleBarEnabled = false
            isZoomControlEnabled = false
        }
        ActivityCompat.requestPermissions(this, permissions,
            SpecificActivity.LOCATION_PERMISSION_REQUEST_CODE
        )
        val marker = Marker()
        marker.position = location
        marker.icon = OverlayImage.fromResource(R.drawable.ic_specific_location_shape)
        marker.map = naverMap
        val cameraUpdate = CameraUpdate.scrollTo(location)
        naverMap.moveCamera(cameraUpdate)
    }
}