package com.bitc.foodytripapp.SpringServer

import com.bitc.foodytripapp.Data.*
import retrofit2.Call
import retrofit2.http.*

interface SpringNetwork {

    @POST("user/joinUser")
    fun joinUser(@Body user : UserModel) : Call<Void>

    @POST("review/insertReview")
    fun insertReview(@Body review : ReviewModel): Call<Void>

    @GET("review/allReviews")
    fun getAllReviews() : Call<ReviewDtoList>

    @GET("review/myReviews/{writer}")
    fun getMyReviews(@Path("writer") writer : String) : Call<ReviewDtoList>

    @GET("review/placeReviews")
    fun getPlaceReviews(@Query("x") x : String,
                        @Query("y") y : String): Call<ReviewDtoList>

    @POST("reply/insertReply")
    fun insertReply(@Body reply : ReplyModel) : Call<Void>

    @GET("reply/getReplies/{reviewId}")
    fun getReplies(@Path("reviewId") reviewId : Int) : Call<ReplyDtoList>

    @GET("reply/getReplyNum/{reviewId}")
    fun getReplyNum(@Path("reviewId") reviewId: Int) : Call<Int>

}