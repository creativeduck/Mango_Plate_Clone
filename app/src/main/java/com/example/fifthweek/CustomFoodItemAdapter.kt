package com.example.fifthweek

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fifthweek.databinding.CustomMainFoodItemBinding

class CustomFoodItemAdapter : RecyclerView.Adapter<CustomFoodItemAdapter.Holder>() {
    var foodList = ArrayList<MainFoodItem>()
    var listener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CustomMainFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setFoodItem(foodList[position])
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    inner class Holder(val binding: CustomMainFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                if (listener != null) {
                    listener!!.onItemClicked(binding.root, adapterPosition)
                }
            }
        }

        fun setFoodItem(item: MainFoodItem) {
            binding.itemImage.setImageResource(item.image)
            binding.itemNo.text = "${item.no}."
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