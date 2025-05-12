package com.example.boblim_photographyfinal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PaymentMethodBottomSheetFragment(
    private val onMethodSelected: (String) -> Unit
) : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_payment_method_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<LinearLayout>(R.id.gpayLayout).setOnClickListener {
            onMethodSelected("GPay")
            dismiss()
        }
        view.findViewById<LinearLayout>(R.id.cardLayout).setOnClickListener {
            onMethodSelected("Card")
            dismiss()
        }
        view.findViewById<LinearLayout>(R.id.walkinLayout).setOnClickListener {
            onMethodSelected("Walk-in")
            dismiss()
        }
    }
} 