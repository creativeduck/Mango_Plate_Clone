package com.example.fifthweek.tastey_data

data class Paragon (
    val ParagonRestaurant : List<ParagonRestaurant>
)

data class ParagonRestaurant(
    val head : List<Any?>,
    val row : List<RowP>
)

data class RowP(
    val SUM_YY : String,
    val QU : String,
    val SIGUN_NM : String,
    val SIGUN_CD : String,
    val BIZCOND_NM : String,
    val MAIN_MENU_NM : String,
    val BIZESTBL_NM : String,
    val TELNO : String?,
    val REFINE_LOTNO_ADDR : String,
    val REFINE_ROADNM_ADDR : String,
    val REFINE_ZIP_CD : String,
    val REFINE_WGS84_LOGT : String,
    val REFINE_WGS84_LAT : String
)