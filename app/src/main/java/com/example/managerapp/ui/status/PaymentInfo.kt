package com.example.managerapp.ui.status

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class PaymentInfo(
    val id: Long,
    val userId: Long,
    val reservationId: Long,
    val date: Date,
    val method: String,
    val cashReceipt: String,
    val paymentAmount: Long
) : Parcelable
