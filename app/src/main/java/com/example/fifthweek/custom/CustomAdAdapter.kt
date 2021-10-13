package com.example.fifthweek.custom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fifthweek.OnItemClickListener
import com.example.fifthweek.databinding.CustomAdItemBinding

class CustomAdAdapter : RecyclerView.Adapter<CustomAdAdapter.Holder>() {
    var imageList = listOf<Int>()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CustomAdItemBinding.inflate(LayoutInflater.from(parent.context),
                                                        parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val image = imageList[position%imageList.size]
        holder.setImage(image)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class Holder(val binding: CustomAdItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener( {
                var pos: Int = adapterPosition
                if(listener != null) {
                    listener?.onItemClicked(binding.root, pos)
                }
            })

        }
        fun setImage(Id: Int) {
            binding.imageView.setImageResource(Id)
        }
    }
}