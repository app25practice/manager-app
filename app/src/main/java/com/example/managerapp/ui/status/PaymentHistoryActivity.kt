package com.example.managerapp.ui.status

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerapp.databinding.ActivityPaymentHistoryBinding
import com.example.managerapp.ui.status.adapter.CompanionHistoryRecyclerViewAdapter
import com.example.managerapp.ui.status.data.PaymentInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setPaymentInfo()
        setCompanionHistoryRecyclerViewAdapter()
    }

    private fun setPaymentInfo() {
        // 테스트 용 데이터
        val paymentInfo = PaymentInfo(
            id = 100, userId = 11, reservationId = 9600, date = Date(), method = "토스로 결제",
            cashReceipt = "미발행", paymentAmount = 50000
        )

        val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREAN)

        binding.paymentTimeInformationTextView.text = dateFormat.format(paymentInfo.date)
        binding.paymentMethodInformationTextView.text = paymentInfo.method
        binding.cashReceiptInformationTextView.text = paymentInfo.cashReceipt
        binding.totalPaymentAmountInformationTextView.text =
            paymentInfo.paymentAmount.toString().plus(" 원")
    }

    private fun setCompanionHistoryRecyclerViewAdapter() {
        val adapter = CompanionHistoryRecyclerViewAdapter()
        binding.companionHistoryRecyclerView.adapter = adapter
        binding.companionHistoryRecyclerView.layoutManager = LinearLayoutManager(this)

        val mock = listOf("24.08.26 21:00 방문 픽업", "24.08.26 22:00 귀가")
        adapter.submitList(mock)
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}