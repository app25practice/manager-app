package com.example.managerapp.ui.status

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.managerapp.databinding.ActivityReservationDetailsBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setReservationInfo()
    }

    private fun setReservationInfo() {
        val reservationInfo: ReservationInfo? = intent.getParcelableExtra("ReservationInfo")
        reservationInfo?.let {
            val dateFormat = SimpleDateFormat("yy.MM.dd a h시", Locale.KOREAN)

            binding.locationDepartTextView.text = it.reservationDetails.departureLocation
            binding.locationArriveTextView.text = it.reservationDetails.arrivalLocation
            binding.companionDepartTimeInformationTextView.text =
                dateFormat.format(it.reservationDetails.date)
            binding.transportationInformationTextView.text = it.reservationDetails.transportation
            binding.requestDetailsInformationTextView.text = it.reservationDetails.message

            binding.userNameInformationTextView.text = it.userInfo.name
            binding.userGenderInformationTextView.text = it.userInfo.gender
            binding.userBirthInformationTextView.text = it.userInfo.birth.plus("-*******")
            binding.userPhoneNumberInformationTextView.text = it.userInfo.phoneNumber
        }
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}