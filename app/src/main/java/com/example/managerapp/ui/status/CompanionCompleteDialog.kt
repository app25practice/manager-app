package com.example.managerapp.ui.status

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import com.example.managerapp.databinding.ViewCompanionCompleteDialogBinding

class CompanionCompleteDialog(context: Context, reservationInfo: ReservationInfo) :
    Dialog(context) {
    private lateinit var binding: ViewCompanionCompleteDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewCompanionCompleteDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBackgroundDrawable()
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setDialogBtnListener()
    }

    private fun setBackgroundDrawable() {
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setDialogBtnListener() {
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        /* 화면 이동 및 예약 정보도 같이 전달 */
        binding.navigateToReportWriteBtn.setOnClickListener {
            Toast.makeText(binding.root.context, "리포트 작성 화면으로 이동", Toast.LENGTH_SHORT).show()
        }
    }
}