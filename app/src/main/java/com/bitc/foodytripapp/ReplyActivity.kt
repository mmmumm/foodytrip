package com.bitc.foodytripapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitc.foodytripapp.Data.ReplyDtoList
import com.bitc.foodytripapp.Data.ReplyModel
import com.bitc.foodytripapp.SpringServer.SpringApplication
import com.bitc.foodytripapp.databinding.ActivityReplyBinding
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReplyActivity : AppCompatActivity() {

    lateinit var binding: ActivityReplyBinding
    lateinit var auth: FirebaseAuth
    lateinit var adapter: ReplyAdapter
    var replyNum: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReplyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reviewId = intent.getIntExtra("reviewId", 0)
        val content = intent.getStringExtra("content")
        val writer = intent.getStringExtra("writer")

        binding.writer.text = writer
        binding.content.text = content

        val springNetwork = (applicationContext as SpringApplication).springNetwork
        val ReplyListCall = springNetwork.getReplies(reviewId)

        ReplyListCall.enqueue(object : Callback<ReplyDtoList> {
            override fun onResponse(call: Call<ReplyDtoList>, response: Response<ReplyDtoList>) {
                binding.recyclerReply.layoutManager = LinearLayoutManager(this@ReplyActivity)

                adapter = ReplyAdapter(this@ReplyActivity, response.body()?.replyDtoList)
                binding.recyclerReply.adapter = adapter

                replyNum = response.body()?.replyDtoList?.count()

                if (response.body() == null) {
                    Log.d("myLog", "댓글이 없습니다.")
                }
            }

            override fun onFailure(call: Call<ReplyDtoList>, t: Throwable) {
                Log.d("myLog", "댓글 db 연결 실패")
            }
        })

        auth = FirebaseAuth.getInstance()
        val loginUser = auth.currentUser?.displayName

        binding.replyBtn.setOnClickListener {

            if (loginUser != null && loginUser != "") {
                val reply = binding.reply.text.toString()
                val replier = auth.currentUser!!.displayName!!

                var replyModel = ReplyModel(
                    reply = reply,
                    replier = replier,
                    reviewId = reviewId
                )
                val springNetwork = (applicationContext as SpringApplication).springNetwork
                val insertReplyCall = springNetwork.insertReply(replyModel)

                insertReplyCall.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.d("myLog", "댓글 db 저장")
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("myLog", "댓글 db 저장 실패...")
                        call.cancel()
                    }
                })

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

                binding.reply.text.clear()

            } else {
                Toast.makeText(baseContext, "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}