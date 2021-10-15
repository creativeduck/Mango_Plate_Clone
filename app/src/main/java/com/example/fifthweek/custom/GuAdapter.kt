package com.example.fifthweek.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fifthweek.OnItemClickListener
import com.example.fifthweek.WhatGu
import com.example.fifthweek.databinding.GuItemBinding

class GuAdapter : RecyclerView.Adapter<GuAdapter.Holder>() {
    var guList = ArrayList<WhatGu>()
    var listener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = GuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setGu(guList[position])
    }

    override fun getItemCount(): Int {
        return guList.size
    }

    inner class Holder(val binding: GuItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnGu.setOnClickListener {
                if (listener!= null) {
                    listener!!.onItemClicked(binding.btnGu, adapterPosition)
                }
            }
        }

        fun setGu(item: WhatGu) {
            binding.btnGu.text = item.guName
        }
    }

}