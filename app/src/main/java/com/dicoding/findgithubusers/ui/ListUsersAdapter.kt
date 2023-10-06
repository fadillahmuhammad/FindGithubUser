package com.dicoding.findgithubusers.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.findgithubusers.data.remote.response.ItemsItem
import com.dicoding.findgithubusers.databinding.ItemRowUserBinding

class ListUsersAdapter : ListAdapter<ItemsItem, ListUsersAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
    }

    class MyViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(users: ItemsItem) {
            Glide.with(binding.imgItemPhoto.context)
                .load(users.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.tvItemUsername.text = "${users.login}"

            binding.root.setOnClickListener {
                openDetailPage(users)
            }
        }

        private fun openDetailPage(users: ItemsItem) {
            val intent = Intent(binding.root.context, DetailsUserActivity::class.java)
            intent.putExtra(USERNAME_KEY, users.login)
            intent.putExtra(AVATAR_KEY, users.avatarUrl)
            binding.root.context.startActivity(intent)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }

        const val USERNAME_KEY = "username_data"
        const val AVATAR_KEY = "avatar_data"
    }
}