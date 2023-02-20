package com.bitc.foodytripapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.bitc.foodytripapp.Data.ReplyDtoList
import com.bitc.foodytripapp.Data.ReviewDto
import com.bitc.foodytripapp.SpringServer.SpringApplication
import com.bitc.foodytripapp.databinding.ReviewRecyclerviewBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat

class ReviewViewHolder(val binding : ReviewRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class ReviewAdapter(var context : Context, val datas : MutableList<ReviewDto>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var auth : FirebaseAuth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReviewViewHolder(
            ReviewRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ReviewViewHolder).binding

        val model = datas!![position]
        val date : String = SimpleDateFormat("yyyy.MM.dd").format(model.createDate)

        auth = FirebaseAuth.getInstance()
        val loginUser = auth.currentUser?.displayName

        if (loginUser=="" || loginUser==null || loginUser != model.writer) {
            binding.updateBtn.visibility = View.GONE
            binding.deleteBtn.visibility = View.GONE
        } else {
            binding.updateBtn.visibility = View.VISIBLE
            binding.deleteBtn.visibility = View.VISIBLE
        }

        val springNetwork = (context.applicationContext as SpringApplication).springNetwork
        val ReplyNumCall = springNetwork.getReplyNum(model.reviewId)

        ReplyNumCall.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.body() == null || response.body() == 0) {
                    binding.replyNum.text = "0"
                } else {
                    binding.replyNum.text = response.body()!!.toString()
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("myLog", "댓글 개수 db 연결 실패")
            }
        })

        binding.placeName.text = model.placeName
        binding.writer.text = model.writer
        binding.starRate.rating = model.starRate.toFloat()
        binding.content.text = model.content
        binding.createDate.text = date

        binding.replyNum.setOnClickListener {
            val intent = Intent(context, ReplyActivity::class.java)

            intent.putExtra("reviewId", model!!.reviewId)
            intent.putExtra("content", model!!.content)
            intent.putExtra("writer", model!!.writer)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }
}


