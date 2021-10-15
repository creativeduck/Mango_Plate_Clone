package com.example.fifthweek.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.fifthweek.R
import com.example.fifthweek.databinding.SelectItemBinding

class SelectFragment : Fragment() {
    var binding : SelectItemBinding? = null
    var name : String? = null
    var gu : String? = null
    var image : String? = null
    var pos : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SelectItemBinding.inflate(inflater, container, false)
        val view = binding!!.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = arguments?.getString("name")
        gu = arguments?.getString("gu")
        image = arguments?.getString("image")
        pos = arguments?.getInt("pos")
        if (binding != null) {
            Glide.with(requireContext())
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.food_item_bossam)
                .into(binding!!.selectImage)
            binding!!.selectName.text = name
            binding!!.selectGu.text = gu
            binding!!.selectNo.text = "${pos}."
        }
    }








    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}