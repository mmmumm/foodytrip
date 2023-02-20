package com.bitc.foodytripapp.KakaoAPI

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KakaoApplication : Application() {
    companion object {
        val API_KEY = "KakaoAK 여러분의 카카오api key 넣으세요"
        val BASE_URL = "https://dapi.kakao.com/"
        val CATEGORY_GROUP = "FD6"

        var kakaoAPI : KakaoAPI
        val retrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        init {
            kakaoAPI = retrofit.create(KakaoAPI::class.java)
        }
    }
}