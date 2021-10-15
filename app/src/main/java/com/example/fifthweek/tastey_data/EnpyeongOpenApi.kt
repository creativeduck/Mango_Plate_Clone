package com.example.fifthweek.tastey_data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


class EnpyeongOpenApi {
    companion object {
        const val baseUrl = "http://openAPI.ep.go.kr:8088/"
        const val API_KEY = "61526648566d6f7335346b676d504f"
    }
}


interface EnpyeongOpenService {
    @GET("{key}/json/{gu}/{startIndex}/{endIndex}")
    fun getEnpyeong(@Path("key") key: String,
                    @Path("gu") gu: String,
                    @Path("startIndex") startIndex : Int,
                    @Path("endIndex") endIndex: Int) : Call<Enpyeong>
}

interface SedamunOpenService {
    @GET("{key}/json/{gu}/{startIndex}/{endIndex}")
    fun getEnpyeong(@Path("key") key: String,
                    @Path("gu") gu: String,
                    @Path("startIndex") startIndex : Int,
                    @Path("endIndex") endIndex: Int) : Call<Seodamun>
}