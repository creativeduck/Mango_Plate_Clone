package com.ssacproject.thirdweek.room

import androidx.recyclerview.widget.DiffUtil
import com.example.fifthweek.room.MainFoodItem

class MainFoodItemCallback : DiffUtil.ItemCallback<MainFoodItem>() {
    // 굳이 상이하게 설정할 필요가 없어서 그냥 했다.
    override fun areItemsTheSame(oldItem: MainFoodItem, newItem: MainFoodItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MainFoodItem, newItem: MainFoodItem): Boolean {
        return oldItem == newItem
    }
}