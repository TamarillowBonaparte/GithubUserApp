package com.dicoding.githubsubmissionbaru.data.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubsubmissionbaru.data.local.DataUser
import com.dicoding.githubsubmissionbaru.data.ui.DetailUserActivity
import com.dicoding.githubsubmissionbaru.databinding.UserItemBinding

class FavoritAdapter(private val dataset: List<DataUser>) :RecyclerView.Adapter<FavoritAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MyViewHolder(binding)
}

override fun getItemCount() = dataset.size

override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    val user = dataset[position]
    holder.binding.username.text = user.username
    Glide.with(holder.itemView.context).load(user.avatarUrl).into(holder.binding.imageView)
    holder.itemView.setOnClickListener {
        val intent = Intent(holder.itemView.context, DetailUserActivity::class.java)
        intent.putExtra("username", user.username)
        holder.itemView.context.startActivity(intent)
    }
}

inner class MyViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)
}