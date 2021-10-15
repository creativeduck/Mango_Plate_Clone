package com.example.fifthweek

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fifthweek.databinding.SelectItemBinding
import com.example.fifthweek.room.GuItem
import com.ssacproject.thirdweek.room.GuItemCallback

class SelectItemListAdapter : ListAdapter<GuItem, SelectItemListAdapter.Holder>(GuItemCallback()) {
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SelectItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setSelect(currentList[position], position)
    }

    inner class Holder(val binding: SelectItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if(listener != null) {
                    listener?.onItemClicked(binding.root, adapterPosition)
                }
            }
        }

        fun setSelect(item: GuItem, position: Int) {
            val list = item.imageList
            var len = 0
            if (list.size > 1) {
                len = 1
            }
            Glide.with(binding.root.context)
                .load(item.imageList[len])
                .centerCrop()
                .placeholder(R.drawable.food_item_bossam)
                .into(binding.selectImage)
            binding.selectName.text = item.name
            binding.selectGu.text = item.gu
            binding.selectNo.text = "${position+1}"
        }
    }


}