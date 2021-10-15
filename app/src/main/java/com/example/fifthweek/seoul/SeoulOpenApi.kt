package com.example.fifthweek.seoul

import com.example.fifthweek.tastey_data.Seodamun
import com.example.fifthweek.tastey_data.Tastey
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class SeoulOpenApi {
    companion object {
        const val baseUrl = "http://openapi.seoul.go.kr:8088/"

    }
}

interface SeoulOpenService {
    @GET("{key}/json/CrtfcUpsoInfo/{startIndex}/{endIndex}")
    fun getSeoul(@Path("key") key : String,
                 @Path("startIndex") startIndex: Int,
                 @Path("endIndex") endIndex: Int) : Call<Seoul>
}