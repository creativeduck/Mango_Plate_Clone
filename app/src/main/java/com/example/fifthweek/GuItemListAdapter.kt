package com.ssacproject.thirdweek.room

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fifthweek.OnItemClickListener
import com.example.fifthweek.R
import com.example.fifthweek.room.MainFoodItem
import com.example.fifthweek.databinding.CustomMainFoodItemBinding
import com.example.fifthweek.room.GuItem

class GuItemListAdapter : ListAdapter<GuItem, GuItemListAdapter.Holder>(GuItemCallback()) {
//    var helper: RoomHeartHelper? = null
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CustomMainFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setMainFoodItem(currentList[position], position)
    }


    inner class Holder(val binding: CustomMainFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        var mHeart: MenuItem? = null

        init {
            binding.root.setOnClickListener {
                if(listener != null) {
                    listener?.onItemClicked(binding.root, adapterPosition)
                }
            }
        }

        fun setMainFoodItem(item: GuItem, position: Int) {
            val list = item.imageList
            var len = 0
            if (list.size > 1) {
                len = 1
            }
            Glide.with(binding.root.context)
                .load(item.imageList[len])
                .centerCrop()
                .placeholder(R.drawable.food_item_bossam)
                .into(binding.itemImage)
            binding.itemNo.text = "${position+1}."
            binding.itemName.text = item.name
            binding.itemLocation.text = item.address
            binding.itemDistance.text = "${900}m"
            binding.textVisit.text = "${1230}"
            binding.textReview.text = "${2394}"
            binding.itemRating.setTextColor(Color.parseColor("#FFFF5544"))
            binding.itemRating.text = "${4.5}"
        }
    }
}