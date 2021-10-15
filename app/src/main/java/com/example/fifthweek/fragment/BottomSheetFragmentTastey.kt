package com.example.fifthweek.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.example.fifthweek.R
import com.example.fifthweek.ui.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragmentTastey : BottomSheetDialogFragment() {
    lateinit var safeRela : RelativeLayout
    lateinit var paragonRela : RelativeLayout
    lateinit var tasteyRela : RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tastey_bottom, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = context as MainActivity

        safeRela = view.findViewById(R.id.safeRela)
        safeRela.setOnClickListener {
            activity.loadSafe(20)
            dismiss()
        }
        paragonRela = view.findViewById(R.id.paragonRela)
        paragonRela.setOnClickListener {
            activity.loadParagon(20)
            dismiss()
        }
        tasteyRela = view.findViewById(R.id.tasteyRela)
        tasteyRela.setOnClickListener {
            activity.loadTastey(20)
            dismiss()
        }

    }
}