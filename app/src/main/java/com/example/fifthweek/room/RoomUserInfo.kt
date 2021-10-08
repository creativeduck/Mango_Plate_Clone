package com.ssacproject.fourthweek.room

import android.content.Context
import android.os.Parcelable
import androidx.room.*
import kotlinx.coroutines.joinAll
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "room_user1")
data class RoomUserInfo(
    @PrimaryKey(autoGenerate = true)
    val no: Long?,
    @ColumnInfo
    val id : Long? = null,
    @ColumnInfo
    val email : String? = null,
    @ColumnInfo
    val nickname : String? = null,
    @ColumnInfo
    val profile : String? = null
) : Parcelable