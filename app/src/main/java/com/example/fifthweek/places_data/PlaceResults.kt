package com.example.fifthweek.places_data

import com.google.android.libraries.places.api.internal.impl.net.pablo.PlaceResult
import com.google.android.libraries.places.api.model.OpeningHours
import com.google.android.libraries.places.api.model.Place

data class PlaceResults (
    val html_attributions : Array<String>,
    val results : Array<Place>
    )


//data class Place(
//    val geo : PlaceResult.Geometry,
//    val icon : String,
//    val icon_background_color : String,
//    val icon_mask_base_uri : String,
//    val name : String,
//    val opening_hours : OpeningHours
//)