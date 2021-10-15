package com.example.fifthweek.tastey_data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ParagonOpenService {
    @GET("ParagonRestaurant?Type=json&pIndex=1")
    fun getParagon(@Query("KEY") key : String, @Query("pSize") size : Int) : Call<Paragon>

}