package com.bitc.foodytripapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bitc.foodytripapp.KakaoAPI.KakaoApplication
import com.bitc.foodytripapp.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity() {

    lateinit var binding : ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinner()

        binding.searchBtn.setOnClickListener {
            searchKeyword()
        }
    }

    private fun searchKeyword() {

        val city = binding.spinner1.selectedItem.toString()
        val region = binding.spinner2.selectedItem.toString()
        var store : String? = binding.searchText.text.toString()


        var keyword = city + " " + region + " " + store

        if (keyword != null) {
            val call : Call<PlaceListModel> = KakaoApplication.kakaoAPI.getSearchKeyword(
                KakaoApplication.API_KEY,
                KakaoApplication.CATEGORY_GROUP,
                keyword
            )

            call.enqueue(object : Callback<PlaceListModel> {
                override fun onResponse(
                    call: Call<PlaceListModel>,
                    response: Response<PlaceListModel>
                ) {
                    binding.recyclerSearch.layoutManager = LinearLayoutManager(this@SearchActivity)

                    var adapter = ReviewSearchAdapter(this@SearchActivity, response.body()?.documents)
                    binding.recyclerSearch.adapter = adapter

                    if (response.body() == null) {
                        Toast.makeText(this@SearchActivity, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<PlaceListModel>, t: Throwable) {
                    Log.d("myLog", "통신 실패")
                }
            })
        }
    }

    private fun setupSpinner() {

        val city = resources.getStringArray(R.array.spinner_city)
        val adapter1 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, city)
        binding.spinner1.adapter = adapter1

        val cityAll = resources.getStringArray(R.array.spinner_all)
        val seoul = resources.getStringArray(R.array.spinner_seoul)
        val busan = resources.getStringArray(R.array.spinner_busan)
        val daegu = resources.getStringArray(R.array.spinner_daegu)
        val incheon = resources.getStringArray(R.array.spinner_incheon)
        val gwangju = resources.getStringArray(R.array.spinner_gwangju)
        val daejeon = resources.getStringArray(R.array.spinner_daejeon)
        val ulsan = resources.getStringArray(R.array.spinner_ulsan)
        val sejong = resources.getStringArray(R.array.spinner_sejong)
        val gyeonggi = resources.getStringArray(R.array.spinner_gyeonggi)
        val gangwon = resources.getStringArray(R.array.spinner_gangwon)
        val chungbuk = resources.getStringArray(R.array.spinner_chung_buk)
        val chungnam = resources.getStringArray(R.array.spinner_chung_nam)
        val jeonbuk = resources.getStringArray(R.array.spinner_jeon_buk)
        val jeonnam = resources.getStringArray(R.array.spinner_jeon_nam)
        val gyeongbuk = resources.getStringArray(R.array.spinner_gyeong_buk)
        val gyeongnam = resources.getStringArray(R.array.spinner_gyeong_nam)
        val jeju = resources.getStringArray(R.array.spinner_jeju)

        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, cityAll)
        binding.spinner2.adapter = adapter2

        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, cityAll)
                        binding.spinner2.adapter = adapter2
                    }
                    1 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, seoul)
                        binding.spinner2.adapter = adapter2
                    }
                    2 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, busan)
                        binding.spinner2.adapter = adapter2
                    }
                    3 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, daegu)
                        binding.spinner2.adapter = adapter2
                    }
                    4 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, incheon)
                        binding.spinner2.adapter = adapter2
                    }
                    5 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, gwangju)
                        binding.spinner2.adapter = adapter2
                    }
                    6 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, daejeon)
                        binding.spinner2.adapter = adapter2
                    }
                    7 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, ulsan)
                        binding.spinner2.adapter = adapter2
                    }
                    8 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, sejong)
                        binding.spinner2.adapter = adapter2
                    }
                    9 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity,
                            R.layout.spinner_text, gyeonggi)
                        binding.spinner2.adapter = adapter2
                    }
                    10 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, gangwon)
                        binding.spinner2.adapter = adapter2
                    }
                    11 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity,
                            R.layout.spinner_text, chungbuk)
                        binding.spinner2.adapter = adapter2
                    }
                    12 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity,
                            R.layout.spinner_text, chungnam)
                        binding.spinner2.adapter = adapter2
                    }
                    13 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, jeonbuk)
                        binding.spinner2.adapter = adapter2
                    }
                    14 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, jeonnam)
                        binding.spinner2.adapter = adapter2
                    }
                    15 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity,
                            R.layout.spinner_text, gyeongbuk)
                        binding.spinner2.adapter = adapter2
                    }
                    16 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity,
                            R.layout.spinner_text, gyeongnam)
                        binding.spinner2.adapter = adapter2
                    }
                    17 -> {
                        val adapter2 = ArrayAdapter(this@SearchActivity, R.layout.spinner_text, jeju)
                        binding.spinner2.adapter = adapter2
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}