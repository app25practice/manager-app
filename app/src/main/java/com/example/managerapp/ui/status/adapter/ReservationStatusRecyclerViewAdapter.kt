package com.example.managerapp.ui.status.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.databinding.ItemReservationStatusBinding
import com.example.managerapp.ui.status.CompanionCompleteDialog
import com.example.managerapp.ui.status.ReservationDetailsActivity
import com.example.managerapp.ui.status.data.ReservationInfo
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationStatusRecyclerViewAdapter :
    ListAdapter<
            ReservationInfo,
            ReservationStatusRecyclerViewAdapter.ReservationStatusViewHolder,
            >(DiffCallback()) {
    inner class ReservationStatusViewHolder(val binding: ItemReservationStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTv.text = item.userInfo.name
            binding.reservationDateTv.text = dateFormat.format(item.reservationDetails.date)

            binding.companionStartBtn.setOnClickListener {
                binding.companionStartBtn.visibility = View.GONE
                binding.companionCompleteBtn.visibility = View.VISIBLE
            }

            binding.companionCompleteBtn.setOnClickListener {
                val companionCompleteDialog = CompanionCompleteDialog(binding.root.context, item)
                companionCompleteDialog.show()
            }

            binding.showDetailsBtn.setOnClickListener {
                val intent =
                    Intent(binding.root.context, ReservationDetailsActivity::class.java)
                        .putExtra("ReservationInfo", item)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ReservationStatusViewHolder {
        val binding =
            ItemReservationStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationStatusViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReservationStatusViewHolder,
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
            return oldItem.reservationDetails.reservationId == newItem.reservationDetails.reservationId
        }

        override fun areContentsTheSame(
            oldItem: ReservationInfo,
            newItem: ReservationInfo,
        ): Boolean {
            return oldItem.userInfo.id == newItem.userInfo.id &&
                    oldItem.reservationDetails.date == newItem.reservationDetails.date
        }
    }
}
