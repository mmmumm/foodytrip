package com.bitc.foodytripapp.Data

import java.sql.Timestamp

data class ReplyDto(
    var replyId : Int,
    var reply : String,
    var replier : String,

    var reviewId : Int,

    var createDate : Timestamp,
    var updateDate : Timestamp
)

data class ReplyDtoList(
    var replyDtoList : MutableList<ReplyDto>
)
