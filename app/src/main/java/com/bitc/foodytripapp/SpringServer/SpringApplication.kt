package com.bitc.foodytripapp.SpringServer

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SpringApplication : Application() {

    var springNetwork : SpringNetwork
    val retrofit : Retrofit
    get() = Retrofit.Builder()
        // baseUrl에 스프링 서버 주소 넣으세요..
        .baseUrl("스프링 서버 주소 넣기")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    init {
        springNetwork = retrofit.create(SpringNetwork::class.java)
    }
}