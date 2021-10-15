package com.example.fifthweek.tastey_data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class TasteyOpenApi {
    companion object {
        const val baseUrl = "https://openapi.gg.go.kr/"
        const val API_KEY = "0976c973a037499bb1ab3fa62561b013"
    }
}

interface TasteyOpenService {
    @GET("PlaceThatDoATasteyFoodSt?Type=json&pIndex=1")
    fun getTastey(@Query("KEY") key : String, @Query("pSize") size : Int) : Call<Tastey>

}