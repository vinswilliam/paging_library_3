package com.example.testcermati.user.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.testcermati.user.data.UserGithub
import androidx.recyclerview.widget.RecyclerView
import com.example.testcermati.databinding.UserItemBinding


class UserGithubAdapter : PagingDataAdapter<UserGithub, UserGithubAdapter.ViewHolder>(DiffCallback()) {
    private lateinit var recyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                UserItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userDetail = getItem(position)
        userDetail?.let {
            holder.apply {
                bind(userDetail)
                itemView.tag = userDetail
            }
        }
    }

    class ViewHolder(
            private val binding: UserItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserGithub) {
            binding.apply {
                user = item
                executePendingBindings()
            }
        }
    }

}

private class DiffCallback : DiffUtil.ItemCallback<UserGithub>() {

    override fun areItemsTheSame(oldItem: UserGithub, newItem: UserGithub): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserGithub, newItem: UserGithub): Boolean {
        return oldItem == newItem
    }

}