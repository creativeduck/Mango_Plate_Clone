package com.example.fifthweek.kakao_image

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageRetrofitService {
    @Headers("Authorization: KakaoAK bd3d8df3bc7bf2947b7f40e014401a94")
    @GET("/v2/search/image")
    fun requestSearchImage(
        @Query("query") keyword : String,
        @Query("sort") sort : String,
        @Query("page") page: Int,
        @Query("size") size : Int = 10
    ) : Call<Image>
}

object SearchRetrofit {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService() : ImageRetrofitService = retrofit.create(ImageRetrofitService::class.java)
}