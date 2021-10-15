package com.example.fifthweek.seoul

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "room_seoul_1")
data class Row(
    @ColumnInfo
    val BIZCND_CODE: String,
    @ColumnInfo
    val BIZCND_CODE_NM: String,
    @ColumnInfo
    val CGG_CODE: String,
    @ColumnInfo
    val CGG_CODE_NM: String,
    @ColumnInfo
    val COB_CODE: String,
    @ColumnInfo
    val COB_CODE_NM: String,
    @ColumnInfo
    val CRTFC_CHR_ID: String,
    @ColumnInfo
    val CRTFC_CHR_NM: String,
    @ColumnInfo
    val CRTFC_CLASS: String,
    @ColumnInfo
    val CRTFC_GBN: String,
    @ColumnInfo
    val CRTFC_GBN_NM: String,
    @ColumnInfo
    val CRTFC_SNO: String,
    @ColumnInfo
    val CRTFC_UPSO_MGT_SNO: Double,
    @ColumnInfo
    val CRTFC_YMD: String,
    @ColumnInfo
    val CRTFC_YN: String,
    @ColumnInfo
    val CRT_TIME: String,
    @ColumnInfo
    val CRT_USR: String,
    @ColumnInfo
    val FOOD_MENU: String,
    @ColumnInfo
    val GNT_NO: String,
    @ColumnInfo
    val MAP_INDICT_YN: String,
    @ColumnInfo
    val OWNER_NM: String,
    @ColumnInfo
    val RDN_ADDR_CODE: String,
    @ColumnInfo
    val RDN_CODE_NM: String,
    @ColumnInfo
    val RDN_DETAIL_ADDR: String,
    @ColumnInfo
    val TEL_NO: String,
    @ColumnInfo
    val UPD_TIME: String,
    @PrimaryKey
    @ColumnInfo
    val UPSO_NM: String,
    @ColumnInfo
    val UPSO_SNO: String,
    @ColumnInfo
    val USE_YN: String,
    @ColumnInfo
    val X_CNTS: String,
    @ColumnInfo
    val Y_DNTS: String
) : Parcelable