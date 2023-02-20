package com.bitc.foodytripapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bitc.foodytripapp.Data.ReviewModel
import com.bitc.foodytripapp.SpringServer.SpringApplication
import com.bitc.foodytripapp.databinding.ActivityReviewWriteBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewWriteActivity : AppCompatActivity() {

    lateinit var binding : ActivityReviewWriteBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReviewWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val placeName = intent.getStringExtra("name")
        val x = intent.getStringExtra("x")
        val y = intent.getStringExtra("y")

        auth = FirebaseAuth.getInstance()

        binding.placeName.text = placeName

        binding.starRate.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            binding.starRate.rating = rating
        }

        binding.completeBtn.setOnClickListener {
            insertReview(placeName!!, x!!, y!!)

            val i = Intent(this, MainActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
        }
    }

    fun insertReview(placeName : String, x : String, y : String) {
        val content = binding.reviewText.text.toString()
        val starRate : Int = binding.starRate.rating.toInt()
        val writer : String = auth.currentUser!!.displayName.toString()

        var reviewModel = ReviewModel(
            content = content,
            starRate = starRate,
            writer = writer,
            placeName = placeName,
            x = x,
            y = y
        )

        val springNetwork = (applicationContext as SpringApplication).springNetwork
        val reviewInsertCall = springNetwork.insertReview(reviewModel)

        reviewInsertCall.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(baseContext, "리뷰 작성 완료", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(baseContext, "리뷰 저장 오류 발생", Toast.LENGTH_SHORT).show()
                call.cancel()
            }
        })
    }
}