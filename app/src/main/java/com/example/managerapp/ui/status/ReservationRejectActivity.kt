package com.example.managerapp.ui.status

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.managerapp.R
import com.example.managerapp.databinding.ActivityReservationRejectBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationRejectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationRejectBinding
    private var reservationId: Long = Constraints.INVALID_RESERVATION_ID
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationRejectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setReservationInfo()
        setRejectReasonDropDown()
        setReservationRejectBtnListener()
    }

    private fun setReservationInfo() {
        val reservationInfo: ReservationInfo? = intent.getParcelableExtra("reservationInfo")
        reservationId = intent.getLongExtra("reservationId", Constraints.INVALID_RESERVATION_ID)

        reservationInfo?.let {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = it.userInfo.name
            binding.reservationDateTextView.text = dateFormat.format(it.reservationDetails.date)
        }
    }

    private fun setRejectReasonDropDown() {
        val rejectReasonOptions = resources.getStringArray(R.array.reservation_reject_option)

        val arrayAdapter = ArrayAdapter(this, R.layout.item_dropdown, rejectReasonOptions)
        binding.reservationRejectReasonAutoCompleteTextView.setAdapter(arrayAdapter)

        binding.reservationRejectReasonAutoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val reservationRejectReason = parent.getItemAtPosition(position).toString()
            Toast.makeText(this, "선택된 값: $reservationRejectReason", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setReservationRejectBtnListener() {
        binding.reservationRejectBtn.setOnClickListener {
            val rejectReason = binding.reservationRejectReasonAutoCompleteTextView.text

            if (rejectReason.isEmpty()) {
                Toast.makeText(this, "거절 사유를 선택해 주세요(필수)", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, ReservationStatusActivity::class.java)
                intent.putExtra("reservationId", reservationId)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}