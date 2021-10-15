package com.example.fifthweek.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.example.fifthweek.R
import com.example.fifthweek.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    lateinit var seekbar : SeekBar
    lateinit var distanceText : TextView
    var pos = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.distance_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seekbar = view.findViewById(R.id.seekBar)
        distanceText = view.findViewById(R.id.distance_text)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekbar: SeekBar?, progress: Int, fromUser: Boolean) {
                when (progress) {
                    0 -> distanceText.text = "100m"
                    1 -> distanceText.text = "300m"
                    2 -> distanceText.text = "500m"
                    3 -> distanceText.text = "1km"
                    else -> distanceText.text = "3km"
                }
                pos = progress
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
                val activity = context as MainActivity
                activity.changeDistance(pos)
                dismiss()

            }
        })
    }

}