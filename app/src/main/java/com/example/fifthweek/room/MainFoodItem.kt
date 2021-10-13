package com.example.fifthweek.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "room_food_enpyeong2")
data class MainFoodItem(
    @PrimaryKey
    @ColumnInfo
    val name: String,
    @ColumnInfo
    val location : String,
    @ColumnInfo
    val distance: Int,
    @ColumnInfo
    val textVisit: Int,
    @ColumnInfo
    val textReview: Int,
    @ColumnInfo
    val rating: Double,
    @ColumnInfo
    val image: String,
    @ColumnInfo
    val confirm: Boolean
) : Parcelable