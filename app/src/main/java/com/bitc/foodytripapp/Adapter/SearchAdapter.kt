package com.bitc.foodytripapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitc.foodytripapp.databinding.SearchRecyclerviewBinding

class SearchViewHolder(val binding : SearchRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class SearchAdapter(var context : Context, val datas : MutableList<PlaceModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(
            SearchRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as SearchViewHolder).binding
        val model = datas!![position]

        binding.name.text = model.place_name
        binding.address.text = model.address

        binding.root.setOnClickListener {
            val intent = Intent(context, PlaceDetailActivity::class.java)

            intent.putExtra("name", model?.place_name)
            intent.putExtra("category", model?.category)
            intent.putExtra("address", model?.address)
            intent.putExtra("phone", model?.phone)
            intent.putExtra("x", model?.x)
            intent.putExtra("y", model?.y)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }
}

class ReviewSearchAdapter(var context : Context, val datas : MutableList<PlaceModel>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SearchViewHolder(
            SearchRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as SearchViewHolder).binding
        val model = datas!![position]

        binding.name.text = model.place_name
        binding.address.text = model.address

        binding.root.setOnClickListener {
            val intent = Intent(context, ReviewWriteActivity::class.java)
            intent.putExtra("name", model?.place_name)
            intent.putExtra("x", model?.x)
            intent.putExtra("y", model?.y)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }
}