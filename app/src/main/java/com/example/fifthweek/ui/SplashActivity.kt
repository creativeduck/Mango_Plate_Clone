package com.example.fifthweek.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.facebook.CallbackManager
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.facebook.FacebookException

import com.facebook.login.LoginResult

import com.facebook.FacebookCallback

import android.R
import android.view.View

import com.facebook.login.widget.LoginButton
import android.widget.Toast
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import androidx.core.content.ContextCompat










class SplashActivity : AppCompatActivity() {
    private suspend fun startSplash() {
        withContext(Default) {
            delay(1000)
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo { _, error ->
                    if ( error!= null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError() == true) {
                            // 로그인 필요
                            startLoginActivity()

                        } else {
                            // 기타 에러
                            error.printStackTrace()
                            Log.e("KAKAO_LOGIN", "${error.printStackTrace()}")
                        }
                    }
                    else {
                        // 토큰 유효성 체크 성공
                        startMainActivity()
                    }
                }
            }
            else {
                // 로그인 필요
                startLoginActivity()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Default).launch {
            startSplash()
        }
    }

    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }


    override fun onStop() {
        super.onStop()
        Log.d("LIFE", "splahs onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFE", "splah onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFE", "spalh onDestroy()")
    }
}