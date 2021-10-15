package com.example.fifthweek.tastey_data

data class Safe (
    val SafetyRestrntInfo : List<SafetyRestrntInfo>
)

data class SafetyRestrntInfo(
    val head : List<Any?>,
    val row : List<RowS>
)

data class RowS(
    val SAFETY_RESTRNT_NO : Int,
    val REFINE_WGS84_LOGT : String,
    val SIGNGU_NM : String,
    val BIZPLC_NM: String,
    val DETAIL_ADDR : String,
    val INDUTYPE_NM: String,
    val INDUTYPE_DETAIL_NM : String,
    val TELNO: String,
    val SLCTN_YN_DIV : String,
    val CANCL_DE: String?,
    val RM_MATR : String?,
    val UPD_DAY : String?,
    val REFINE_ROADNM_ADDR : String,
    val REFINE_LOTNO_ADDR : String,
    val REFINE_ZIPNO : String,
    val REFINE_WGS84_LAT : String,
    val SIDO_NM : String
)