package com.ssacproject.thirdweek.room

import androidx.recyclerview.widget.DiffUtil
import com.example.fifthweek.room.GuItem
import com.example.fifthweek.room.MainFoodItem

class GuItemCallback : DiffUtil.ItemCallback<GuItem>() {
    override fun areItemsTheSame(oldItem: GuItem, newItem: GuItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GuItem, newItem: GuItem): Boolean {
        return oldItem == newItem
    }
}