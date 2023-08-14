package com.example.mynearbystore.ui.store

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mynearbystore.data.local.model.StoresItemLocal
import com.example.mynearbystore.databinding.ListItemStoreBinding
import com.example.mynearbystore.util.format

class StoreAdapter : ListAdapter<StoresItemLocal, RecyclerView.ViewHolder>(diffUtilCallback) {

    private inner class ViewHolder(private val binding: ListItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onItemOnClickListener?.let {
                    it(getItem(bindingAdapterPosition), bindingAdapterPosition)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(storesItemLocal: StoresItemLocal) {
            binding.txtStoreName.text = storesItemLocal.storeName
            binding.txtAccountName.text = storesItemLocal.accountName
            binding.txtAreaName.text = storesItemLocal.areaName
            binding.txtDistance.text = "${storesItemLocal.distance?.format(2)}km"
            binding.imageView.visibility = if (storesItemLocal.checklist == true) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ListItemStoreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as ViewHolder
        val storesItemLocal = currentList[position]
        holder.bind(storesItemLocal)
    }

    private var onItemOnClickListener: ((StoresItemLocal, Int) -> Unit)? = null
    fun setOnItemClickListener(listener: (StoresItemLocal, Int) -> Unit) {
        onItemOnClickListener = listener
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<StoresItemLocal>() {
    override fun areItemsTheSame(oldItem: StoresItemLocal, newItem: StoresItemLocal): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: StoresItemLocal, newItem: StoresItemLocal): Boolean {
        return oldItem == newItem
    }
}