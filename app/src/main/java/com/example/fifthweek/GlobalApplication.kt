package com.example.fifthweek

import android.app.Application
import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import com.facebook.FacebookSdk
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import java.lang.IllegalStateException


class GlobalApplication : Application() {
    companion object {
        var appContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("iyrimg8mf0")

   }

}