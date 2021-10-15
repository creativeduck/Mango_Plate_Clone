package com.example.fifthweek.near

import com.example.fifthweek.tastey_data.Seodamun
import com.example.fifthweek.tastey_data.Tastey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class NearPlaceOpenApi {
    companion object {
        const val baseUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/"

    }
}

interface NearPlaceOpenService {
    @GET("json")
    fun getNearPlace(@Query("key") key : String,
                 @Query("location") location: String,
                 @Query("radius") radius: Int,
                 @Query("language") language : String,
                 @Query("type") type: String) : Call<NearPlace>
}