package com.example.managerapp.ui.status.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.databinding.ItemCompanionCompleteHistoryBinding
import com.example.managerapp.ui.status.PaymentHistoryActivity
import com.example.managerapp.ui.status.data.ReservationInfo
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

            binding.userNameTextView.text = item.userInfo.name
            binding.reservationDateTextView.text = dateFormat.format(item.reservationDetails.date)

            binding.showDetailsBtn.setOnClickListener {
                val intent =
                    Intent(binding.root.context, PaymentHistoryActivity::class.java)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CompanionCompleteViewHolder {
        val binding = ItemCompanionCompleteHistoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
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