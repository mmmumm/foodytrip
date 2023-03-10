package com.bitc.foodytripapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bitc.foodytripapp.Data.UserModel
import com.bitc.foodytripapp.SpringServer.SpringApplication
import com.bitc.foodytripapp.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JoinActivity : AppCompatActivity() {

    lateinit var binding : ActivityJoinBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.joinBtn.setOnClickListener {
            val userId = binding.joinId.text.toString()
            val password = binding.joinPw.text.toString()
            val email = binding.joinEmail.text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    binding.joinId.text.clear()
                    binding.joinPw.text.clear()
                    binding.joinEmail.text.clear()
                    if (task.isSuccessful) {
                        var user = auth.currentUser
                        var profileUpdates = UserProfileChangeRequest.Builder().setDisplayName(userId).build()
                        user!!.updateProfile(profileUpdates)
                        var userModel = UserModel(
                            userId = userId,
                            password = password,
                            email = email
                        )
                        val springNetwork = (applicationContext as SpringApplication).springNetwork
                        val userJoinCall = springNetwork.joinUser(userModel)
                        userJoinCall.enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                Log.d("myLog", "?????? db ??????")
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Log.d("myLog", "?????? db ?????? ??????...")
                                call.cancel()
                            }
                        })
                        auth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener{ sendTask ->
                                if (sendTask.isSuccessful) {
                                    Toast.makeText(baseContext, "???????????? ??????. ????????? ?????? ?????? ????????????.",
                                        Toast.LENGTH_SHORT).show()
                                    val i = Intent(this, MainActivity::class.java)
                                    i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(i)
                                } else {
                                    Toast.makeText(baseContext, "?????? ?????? ??????", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(baseContext, "???????????? ??????", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}