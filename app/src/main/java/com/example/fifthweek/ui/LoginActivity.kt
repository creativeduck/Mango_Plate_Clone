package com.example.fifthweek.ui

import android.accounts.Account
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.fifthweek.R
import com.example.fifthweek.databinding.ActivityLoginBinding
import com.facebook.*
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONObject
import java.util.logging.LogRecord


class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    val TAG = "KAKAO_LOGIN"

    lateinit var callbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textSkip.setOnClickListener {
            startMainActivity()
        }

        binding.loginKakao.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                UserApiClient.instance.loginWithKakaoTalk(this@LoginActivity, prompts = listOf(Prompt.LOGIN), callback =
                callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this@LoginActivity, prompts = listOf(Prompt.LOGIN), callback =
                callback)
            }
        }

        callbackManager = CallbackManager.Factory.create()
        binding.loginFacebook.setReadPermissions("email")
        binding.loginFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(result: LoginResult?) {
                requestMe(result!!.accessToken)
                startMainActivity()
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }

            fun requestMe(token: AccessToken) {
                var userEmail: String? = null
                var userName : String? = null
                var userPicture : String? = null
                val graphRequest = GraphRequest.newMeRequest(token,
                    object: GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                            Log.d("result", `object`.toString())
                            userEmail = `object`?.getString("email")
                            userName = `object`?.getString("name")
                            userPicture = `object`?.getString("url")
                        }
                    })
                setSharedData("faceEmail", "faceEmail", userEmail)
                setSharedData("faceName", "faceName", userName)
                setSharedData("facePic", "facePic", userPicture)
                val parameters = Bundle()
                parameters.putString("fields", "name,email,picture")
                graphRequest.parameters = parameters
                graphRequest.executeAsync()
            }

        })


        binding.loginFacebookTmp.setOnClickListener {
            binding.loginFacebook.performClick()
        }
    }

    internal val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
           Log.e(TAG, "로그인 실패", error)
        } else if (token != null) {
            UserApiClient.instance.me { user, error ->
                Log.i(TAG, "로그인 성공 ${token.accessToken}" )

                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                    } else {
                        val user1 = user!!.kakaoAccount
                    }
                }
                val kakaoId = user!!.id
                setSharedData("kakaoID", "kakaoID", kakaoId)
                startMainActivity()
            }
        }
    }

    fun setSharedData(name: String, key: String, data: Long) {
        val pref = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putLong(key, data)
        editor.apply()
    }
    fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFE", "login onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LIFE", "login onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFE", "login onDestroy()")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("requestMyCode", "${requestCode}")
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun setSharedData(name: String, key: String, data: String?) {
        val pref = getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, data)
        editor.apply()
    }
}