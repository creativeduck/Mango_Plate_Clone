package com.example.fifthweek


import android.util.Log
import com.example.fifthweek.places_data.PlaceResults
import com.example.fifthweek.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NearbyPlaceService {
    @GET("/maps/api/place/nearbysearch/json?")
    fun loadPlaces(@Query("key") key : String, @Query("location") location : String,
                   @Query("radius") radius : Int, @Query("language") language : String,
                   @Query("type") type: String) : Call<PlaceResults>
}


class PlaceClient {
    companion object {
        const val baseUrl = "https://maps.googleapis.com"

    }


//    val retrofit = Retrofit.Builder()
//        .baseUrl(baseUrl)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val service = retrofit.create(NearbyPlaceService::class.java)
}

//class PlaceRepository {
//
//    fun loadPlace(mCallback: MainActivity) {
//        val call = PlaceClient.service.loadPlaces("AIzaSyBUuFtQpyriYA5wfAZC52162UXcyk1BO6s",
//                "35.0,126.0", Integer(1000),"ko", "restaurant")
//
//        call.enqueue(object : Callback<PlaceResults> {
//            override fun onFailure(call: Call<PlaceResults>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//            override fun onResponse(call: Call<PlaceResults>, response: Response<PlaceResults>) {
//                if (response.isSuccessful) {
//                    mCallback.loadComplete(response.body()!!.results)
//                } else {
//
//                    Log.d("NAMEIS", "dsf")
//                }
//            }
//        })
//    }
//}