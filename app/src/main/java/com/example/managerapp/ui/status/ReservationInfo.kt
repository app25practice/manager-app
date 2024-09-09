package com.example.managerapp.ui.status

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ReservationInfo(
    val userInfo: UserInfo,
    val reservationDetails: ReservationDetails
) : Parcelable

@Parcelize
data class UserInfo(
    val id: String,
    val name: String,
    val gender: String,
    val birth: String,
    val phoneNumber: String
) : Parcelable

@Parcelize
data class ReservationDetails(
    val date: Date,
    val transportation: String,
    val message: String
) : Parcelable


