package com.example.fifthweek.tastey_data

data class Tastey (
    val PlaceThatDoATasteyFoodSt : List<PlaceThatDoATasteyFoodSt>
)

data class PlaceThatDoATasteyFoodSt(
    val head : List<Any?>,
    val row : List<Row>
)

data class Row(
    val SIGUN_NM : String,
    val SIGUN_CD : String,
    val RESTRT_NM : String,
    val REPRSNT_FOOD_NM : String,
    val TASTFDPLC_TELNO : String,
    val RM_MATR : String?,
    val EFINE_LOTNO_ADDR : String,
    val REFINE_ROADNM_ADDR : String,
    val REFINE_ZIP_CD : String,
    val REFINE_WGS84_LOGT : String,
    val REFINE_WGS84_LAT : String
)