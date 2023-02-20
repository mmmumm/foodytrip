package com.bitc.foodytripapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bitc.foodytripapp.Data.ReplyDto
import com.bitc.foodytripapp.databinding.ReplyRecyclerviewBinding
import java.text.SimpleDateFormat

class ReplyViewHolder(val binding : ReplyRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class ReplyAdapter(var context : Context, val datas : MutableList<ReplyDto>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ReplyViewHolder(
            ReplyRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val binding = (holder as ReplyViewHolder).binding
        val model = datas!![position]
        val date : String = SimpleDateFormat("yyyy.MM.dd").format(model.createDate)

        binding.replier.text = model.replier
        binding.reply.text = model.reply
        binding.createDate.text = date
    }

    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }
}