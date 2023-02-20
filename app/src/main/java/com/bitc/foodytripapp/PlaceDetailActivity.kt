package com.bitc.foodytripapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitc.foodytripapp.Data.ReviewDto
import com.bitc.foodytripapp.Data.ReviewDtoList
import com.bitc.foodytripapp.SpringServer.SpringApplication
import com.bitc.foodytripapp.databinding.ActivityPlaceDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityPlaceDetailBinding
    lateinit var adapter : ReviewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPlaceDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val category = intent.getStringExtra("category")
        val address = intent.getStringExtra("address")
        val phone = intent.getStringExtra("phone")
        val x = intent.getStringExtra("x")
        val y = intent.getStringExtra("y")

        binding.name.text = name
        binding.category.text = category
        binding.address.text = address
        binding.phone.text = phone

        val springNetwork = (applicationContext as SpringApplication).springNetwork
        val placeReviewCall = springNetwork.getPlaceReviews(x!!, y!!)

        placeReviewCall.enqueue(object : Callback<ReviewDtoList> {
            override fun onResponse(call: Call<ReviewDtoList>, response: Response<ReviewDtoList>) {

                binding.recyclerReview.layoutManager = LinearLayoutManager(this@PlaceDetailActivity)
                adapter = ReviewAdapter(this@PlaceDetailActivity, response.body()?.reviewDtoList)
                binding.recyclerReview.adapter = adapter

                if (response.body() == null) {
                    Toast.makeText(this@PlaceDetailActivity, "전체 리뷰가 없습니다.", Toast.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<ReviewDtoList>, t: Throwable) {
                Log.d("myLog", "음식점 리뷰 db 연결 실패")
            }
        })

        binding.reviewBtn.setOnClickListener {
            val intent = Intent(this, ReviewWriteActivity::class.java)

            intent.putExtra("name", name)
            intent.putExtra("x", x)
            intent.putExtra("y", y)

            startActivity(intent)
        }
    }
}