package com.example.managerapp.ui.status

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.databinding.ItemReservationApplyBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ReservationApplyRecyclerViewAdapter(
    private val onItemAccepted: (ReservationInfo) -> Unit,
    private val onItemRefused: (ReservationInfo, Long) -> Unit
) :
    ListAdapter<
            ReservationInfo,
            ReservationApplyRecyclerViewAdapter.ReservationApplyViewHolder,
            >(DiffCallback()) {
    inner class ReservationApplyViewHolder(val binding: ItemReservationApplyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ReservationInfo) {
            val dateFormat = SimpleDateFormat("M월 d일 a h시", Locale.KOREAN)

            binding.userNameTextView.text = item.userInfo.name
            binding.reservationDateTextView.text = dateFormat.format(item.reservationDetails.date)

            binding.acceptBtn.setOnClickListener {
                onItemAccepted(item)
                val reservationApplyList = currentList.toMutableList()
                reservationApplyList.remove(item)
                submitList(reservationApplyList)
            }

            binding.refuseBtn.setOnClickListener {
                onItemRefused(item, item.reservationDetails.reservationId)
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
    ): ReservationApplyViewHolder {
        val binding =
            ItemReservationApplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationApplyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReservationApplyViewHolder,
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