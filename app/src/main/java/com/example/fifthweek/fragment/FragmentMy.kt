package com.example.fifthweek.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.fifthweek.R
import com.example.fifthweek.databinding.FragmentMyBinding
import com.kakao.sdk.user.UserApiClient

class FragmentMy : Fragment() {
    var binding : FragmentMyBinding? = null


    val TAG = "KAKAO_LOGIN"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nickname = getSharedStringData("userNickName", "userNickName")
        val profile = getSharedStringData("userProfile", "userProfile")

        if (nickname != null) {
            binding!!.userNickName.text = nickname

        } else {
            binding!!.userNickName.text = "사용자"
        }
        if (profile != null) {
            Glide.with(this)
                .load(profile)
                .fitCenter()
                .placeholder(R.drawable.food_item_bossam)
                .into(binding!!.userProfile)
        } else {
            binding!!.userProfile.setImageResource(R.drawable.food_item_bossam)

        }

        val linearLogout : LinearLayout = view.findViewById(R.id.linearLogout)
        linearLogout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패", error)
                } else {
                    Log.e(TAG, "로그아웃 성공")
                }
            }
            binding!!.userNickName.text = "사용자"
            binding!!.userProfile.setImageResource(R.drawable.food_item_bossam)
            setSharedData("userNickName", "userNickName", null)
            setSharedData("userProfile", "userProfile", null)
        }

        val linearDisconnect : LinearLayout = view.findViewById(R.id.linearDisconnect)
        linearDisconnect.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e(TAG, "연결 끊기 실패", error)
                } else {
                    Log.e(TAG, "연결 끊기 성공. SDK에서 토큰 삭제됨")
                }
            }
        }




    }
    fun getSharedLongData(name: String, key: String): Long {
        val pref = requireActivity().getSharedPreferences(name, Activity.MODE_PRIVATE)
        if (pref == null) {
            return 0
        } else {
            return pref.getLong(key, 0)
        }
    }
    fun getSharedStringData(name: String, key: String): String? {
        val pref = requireActivity().getSharedPreferences(name, Activity.MODE_PRIVATE)
        return pref.getString(key, null)
    }
    fun setSharedData(name: String, key: String, data: String?) {
        val pref = requireActivity().getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, data)
        editor.apply()
    }
    fun setSharedData(name: String, key: String, data: Long) {
        val pref = requireActivity().getSharedPreferences(name, Activity.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putLong(key, data)
        editor.apply()
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}