package com.bitc.foodytripapp.Data

import java.io.Serializable
import java.sql.Timestamp

data class ReviewDto(
    var reviewId : Int,
    var content : String,
    var starRate : Int,

    var writer : String,

    var placeName : String,
    var x : String,
    var y : String,

    var createDate : Timestamp,
    var updateDate : Timestamp
)

data class ReviewDtoList(
    var reviewDtoList : MutableList<ReviewDto>
)
