package com.bitc.foodytripapp.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitc.foodytripapp.Data.ReviewDto
import com.bitc.foodytripapp.Data.ReviewDtoList
import com.bitc.foodytripapp.ReviewAdapter
import com.bitc.foodytripapp.SpringServer.SpringApplication
import com.bitc.foodytripapp.databinding.FragmentFeedBinding
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedFragment : Fragment() {

    lateinit var binding : FragmentFeedBinding
    lateinit var adapter : ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val springNetwork = (requireContext().getApplicationContext() as SpringApplication).springNetwork
        val allReviewCall = springNetwork.getAllReviews()

        allReviewCall.enqueue(object : Callback<ReviewDtoList> {
            override fun onResponse(call: Call<ReviewDtoList>, response: Response<ReviewDtoList>) {
                if (response.body() == null) {
                    Toast.makeText(requireContext(), "전체 리뷰가 없습니다.", Toast.LENGTH_SHORT).show()
                }

                binding.recyclerFeed.layoutManager = LinearLayoutManager(activity)
                adapter = ReviewAdapter(requireContext(), response.body()?.reviewDtoList)
                binding.recyclerFeed.adapter = adapter
            }

            override fun onFailure(call: Call<ReviewDtoList>, t: Throwable) {
                Log.d("myLog", "전체 리뷰 db 연결 실패")
            }
        })

        return binding.root
    }
}