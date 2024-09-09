package com.example.managerapp.ui.status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.databinding.ItemCompanionCompleteHistoryBinding
import com.example.managerapp.databinding.ItemReservationStatusBinding
import java.text.SimpleDateFormat
import java.util.Locale

class CompanionCompleteHistoryRecyclerViewAdapter :
    ListAdapter<
            ReservationInfo,
            CompanionCompleteHistoryRecyclerViewAdapter.CompanionCompleteViewHolder,
            >(DiffCallback()) {
    inner class CompanionCompleteViewHolder(val binding: ItemCompanionCompleteHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTv.text = item.userInfo.name
            binding.reservationDateTv.text = dateFormat.format(item.reservationDetails.date)

            binding.showDetailsBtn.setOnClickListener {
                Toast.makeText(binding.root.context, "예약 정보 화면 이동", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CompanionCompleteViewHolder {
        val binding = ItemCompanionCompleteHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanionCompleteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CompanionCompleteViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DiffCallback : DiffUtil.ItemCallback<ReservationInfo>() {
        override fun areItemsTheSame(
            oldItem: ReservationInfo,
            newItem: ReservationInfo,
        ): Boolean {
            return oldItem.userInfo.id == newItem.userInfo.id
        }

        override fun areContentsTheSame(
            oldItem: ReservationInfo,
            newItem: ReservationInfo,
        ): Boolean {
            return oldItem.userInfo.name == newItem.userInfo.name &&
                    oldItem.reservationDetails.date == newItem.reservationDetails.date
        }
    }
}