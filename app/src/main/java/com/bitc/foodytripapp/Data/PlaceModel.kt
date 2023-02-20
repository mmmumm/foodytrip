package com.bitc.foodytripapp

import com.google.gson.annotations.SerializedName

data class PlaceListModel(
    var documents : MutableList<PlaceModel>? = null
)

data class PlaceModel(

    var place_name : String,

    @SerializedName("category_name")
    var category : String,

    @SerializedName("road_address_name")
    var address : String,

    var phone : String,

    var x : String,
    var y : String
)
