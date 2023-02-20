package com.bitc.foodytripapp.Fragment

import android.content.Intent
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
import com.bitc.foodytripapp.JoinActivity
import com.bitc.foodytripapp.LoginActivity
import com.bitc.foodytripapp.ReviewAdapter
import com.bitc.foodytripapp.SpringServer.SpringApplication
import com.bitc.foodytripapp.databinding.FragmentMyReviewBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReviewFragment : Fragment() {

    lateinit var binding : FragmentMyReviewBinding
    lateinit var adapter : ReviewAdapter

    lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMyReviewBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()

        if (!checkAuth()) {
            binding.recyclerReview.visibility = View.GONE
            binding.logoutText.visibility = View.VISIBLE
            binding.loginBtn.visibility = View.VISIBLE
            binding.joinBtn.visibility = View.VISIBLE
        } else {
            binding.recyclerReview.visibility = View.VISIBLE
            binding.logoutText.visibility = View.GONE
            binding.loginBtn.visibility = View.GONE
            binding.joinBtn.visibility = View.GONE

            val writer = auth.currentUser!!.displayName

            val springNetwork = (requireActivity().getApplicationContext() as SpringApplication).springNetwork
            val myReviewCall = springNetwork.getMyReviews(writer!!)

            myReviewCall.enqueue(object : Callback<ReviewDtoList> {
                override fun onResponse(call: Call<ReviewDtoList>, response: Response<ReviewDtoList>) {
                    binding.recyclerReview.layoutManager = LinearLayoutManager(activity)
                    adapter = ReviewAdapter(requireContext(), response.body()?.reviewDtoList)
                    binding.recyclerReview.adapter = adapter

                    if (response.body() == null) {
                        Toast.makeText(requireContext(), "전체 리뷰가 없습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ReviewDtoList>, t: Throwable) {
                    Log.d("myLog", "내 리뷰 db 연결 실패")
                }
            })
        }

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        binding.joinBtn.setOnClickListener {
            startActivity(Intent(requireContext(), JoinActivity::class.java))
        }

        return binding.root
    }

    fun checkAuth() : Boolean {
        var currentUser = auth.currentUser

        return currentUser?.let{
            currentUser.isEmailVerified
        }?:let{
            false
        }
    }
}