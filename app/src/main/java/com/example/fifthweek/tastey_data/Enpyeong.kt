package com.example.fifthweek.tastey_data

data class Enpyeong (
    val EpModelRestaurantDesignate : EpModelRestaurantDesignate
)

data class Seodamun(
    val SeodaemunModelRestaurantDesignate : SeodaemunModelRestaurantDesignate
)

data class SeodaemunModelRestaurantDesignate(
    val list_total_count : Int,
    val RESULT : EP_RESULT,
    val row : List<RowE>
)

data class EpModelRestaurantDesignate(
    val list_total_count : Int,
    val RESULT : EP_RESULT,
    val row : List<RowE>
)

data class EP_RESULT(
    val CODE : String,
    val MESSAGE : String
)

data class RowE(
    val CGG_CODE : String,
    val ASGN_YY : String,
    val ASGN_SNO : String,
    val APPL_YMD : String,
    val ASGN_YMD : String,
    val UPSO_NM : String,
    val SITE_ADDR_RD : String,
    val SITE_ADDR : String,
    val PERM_NT_NO : String,
    val SNT_UPTAE_NM : String,
    val MAIN_EDF : String,
    val TRDP_AREA : Double,
    val ADMDNG_NM : String,
    val GRADE_FACIL_GBN : String,
    val UPSO_SITE_TELNO : String
)