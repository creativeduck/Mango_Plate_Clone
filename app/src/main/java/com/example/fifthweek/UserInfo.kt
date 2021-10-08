package com.example.fifthweek

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    var id : Long? = null,
    var email : String? = null,
    var nickname : String? = null,
    var profile : String? = null
) : Parcelable