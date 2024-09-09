package com.example.managerapp.ui.status

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.databinding.ActivityReservationStatusBinding
import com.example.managerpracticeapp.ui.status.ReservationStatusRecyclerViewAdapter
import java.util.Date

class ReservationStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStatusBinding
    private var reservationStatusAdapter = ReservationStatusRecyclerViewAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setReservationStatusRecyclerViewAdapter()
        setReservationApplyRecyclerViewAdapter()
        setCompanionCompleteHistoryRecyclerViewAdapter()
    }

    private fun setReservationStatusRecyclerViewAdapter() {
        binding.reservationStatusRecyclerView.adapter = reservationStatusAdapter
        binding.reservationStatusRecyclerView.layoutManager = LinearLayoutManager(this)

        // mock 테스트
        reservationStatusAdapter.submitList(createMock())
    }

    private fun setCompanionCompleteHistoryRecyclerViewAdapter() {
        val adapter = CompanionCompleteHistoryRecyclerViewAdapter()
        binding.companionCompleteHistoryRecyclerView.adapter = adapter
        binding.companionCompleteHistoryRecyclerView.layoutManager = LinearLayoutManager(this)

        // mock 테스트
        adapter.submitList(createMock())
    }

    private fun setReservationApplyRecyclerViewAdapter() {
        val adapter = ReservationApplyRecyclerViewAdapter(
            onItemAccepted = { item->
                val reservationStatusList = reservationStatusAdapter.currentList.toMutableList()
                reservationStatusList.add(item)
                reservationStatusAdapter.submitList(reservationStatusList)}
        )

        binding.reservationApplyRecyclerView.adapter = adapter
        binding.reservationApplyRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmptyList(adapter)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmptyList(adapter)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmptyList(adapter)
            }

            private fun checkEmptyList(adapter: ReservationApplyRecyclerViewAdapter) {
                if (adapter.itemCount == 0) {
                    binding.reservationApplyTextView.visibility = View.GONE
                } else {
                    binding.reservationApplyTextView.visibility = View.VISIBLE
                }
            }
        })

        adapter.submitList(listOf(ReservationInfo(
            UserInfo(id = "3", name = "이상민", gender = "남", birth = "640630", phoneNumber = "01012345678"),
            ReservationDetails( date = Date(), transportation = "택시", message = "없음")
        )))
    }

    private fun createMock(): List<ReservationInfo> =
        listOf(
            ReservationInfo(
                UserInfo(id = "1", name = "홍길동", gender = "남", birth = "640630", phoneNumber = "01012345678"),
                ReservationDetails( date = Date(), transportation = "택시", message = "없음")
            ),
            ReservationInfo(
                UserInfo(id = "2", name = "강만기", gender = "남", birth = "640630", phoneNumber = "01012345678"),
                ReservationDetails( date = Date(), transportation = "택시", message = "없음")
            )
        )

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}