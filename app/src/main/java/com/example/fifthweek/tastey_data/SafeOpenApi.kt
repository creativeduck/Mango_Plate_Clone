package com.example.fifthweek.tastey_data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SafeOpenService {
    @GET("SafetyRestrntInfo?Type=json&pIndex=1")
    fun getSafe(@Query("KEY") key : String, @Query("pSize") size : Int) : Call<Safe>

}
