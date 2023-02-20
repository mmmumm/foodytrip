package com.bitc.foodytripapp.KakaoAPI

import com.bitc.foodytripapp.PlaceListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoAPI {
    @GET("v2/local/search/keyword.json")
    fun getSearchKeyword (
        @Header("Authorization") key : String,
        @Query("category_group_code") group : String,
        @Query("query") query : String
    ): Call<PlaceListModel>
}