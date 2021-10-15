package com.example.fifthweek.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fifthweek.R
import com.example.fifthweek.databinding.ImageViewBinding

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.Holder>() {
    var imageList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ImageViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setImage(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class Holder(val binding: ImageViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setImage(item: String) {
            Glide.with(binding.root.context)
                .load(item)
                .centerCrop()
                .placeholder(R.drawable.food_item_bossam)
                .into(binding.imageView)
        }
    }

}