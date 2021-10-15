package com.example.fifthweek.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "room_food_gu")
data class GuItem(
    @PrimaryKey
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val gu : String,
    @ColumnInfo
    val type: String,
    @ColumnInfo
    val menu: String,
    @ColumnInfo
    val address: String,
    @ColumnInfo
    val tel: String,
    @ColumnInfo
    val Lat: Double,
    @ColumnInfo
    val Lng: Double,
    @ColumnInfo
    val imageList: ArrayList<String>
) : Parcelable