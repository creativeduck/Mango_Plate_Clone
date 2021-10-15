package com.ssacproject.thirdweek.room

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fifthweek.room.MainFoodItem
import com.example.fifthweek.databinding.CustomMainFoodItemBinding

class MenuItemListAdapter : ListAdapter<MainFoodItem, MenuItemListAdapter.Holder>(MainFoodItemCallback()) {
//    var helper: RoomHeartHelper? = null
//    var listener: OnItemClickListener? = null
//    var longListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CustomMainFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setMainFoodItem(currentList[position], position)
    }


    inner class Holder(val binding: CustomMainFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        var mHeart: MenuItem? = null

//        init {
//            binding.root.setOnClickListener {
//                if(listener != null) {
//                    listener?.onItemClicked(binding.root, adapterPosition)
//                }
//            }
//            binding.root.setOnLongClickListener {
//                if (longListener != null) {
//                    longListener?.onItemLongClicked(binding.root, adapterPosition)
//                    true
//                }
//                false
//            }
//        }

        fun setMainFoodItem(item: MainFoodItem, position: Int) {
            Glide.with(binding.root.context)
                .load(item.image)
                .centerCrop()
                .into(binding.itemImage)
            binding.itemNo.text = "${position+1}."
            binding.itemName.text = item.name
            binding.itemLocation.text = item.location
            binding.itemDistance.text = "${item.distance}m"
            binding.textVisit.text = "${item.textVisit}"
            binding.textReview.text = "${item.textReview}"
            if (item.confirm) {
                binding.itemRating.setTextColor(Color.parseColor("#FFFF5544"))
            } else {
                binding.itemRating.setTextColor(Color.parseColor("#FFEAE8EA"))
            }
            binding.itemRating.text = "${item.rating}"
        }
    }
}