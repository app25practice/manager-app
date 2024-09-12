package com.example.managerapp.ui.status

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.databinding.ActivityReservationStatusBinding
import com.example.managerpracticeapp.ui.status.ReservationStatusRecyclerViewAdapter
import java.util.Date

class ReservationStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReservationStatusBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var reservationApplyAdapter: ReservationApplyRecyclerViewAdapter
    private var reservationStatusAdapter = ReservationStatusRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservationStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        setReservationStatusRecyclerViewAdapter()
        setReservationApplyRecyclerViewAdapter()
        setCompanionCompleteHistoryRecyclerViewAdapter()
        setResultLauncher()
    }

    private fun setResultLauncher() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val reservationId = result.data?.getLongExtra(
                        "reservationId",
                        Constraints.INVALID_RESERVATION_ID
                    )

                    if (reservationId != Constraints.INVALID_RESERVATION_ID) {
                        val reservationApplyList =
                            reservationApplyAdapter.currentList.toMutableList()
                        reservationApplyList.removeAll { it.reservationDetails.reservationId == reservationId }
                        reservationApplyAdapter.submitList(reservationApplyList)
                    }
                }
            }
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
        reservationApplyAdapter = ReservationApplyRecyclerViewAdapter(
            onItemAccepted = { item ->
                val reservationStatusList = reservationStatusAdapter.currentList.toMutableList()
                reservationStatusList.add(item)
                reservationStatusAdapter.submitList(reservationStatusList)
            },

            onItemRefused = { item, reservationId ->
                val intent = Intent(this, ReservationRejectActivity::class.java)
                intent.putExtra("reservationInfo", item)
                intent.putExtra("reservationId", reservationId)
                resultLauncher.launch(intent)
            }
        )

        binding.reservationApplyRecyclerView.adapter = reservationApplyAdapter
        binding.reservationApplyRecyclerView.layoutManager = LinearLayoutManager(this)

        setAdapterDataObserver(reservationApplyAdapter)

        // mock 테스트
        reservationApplyAdapter.submitList(
            listOf(
                ReservationInfo(
                    UserInfo(
                        id = 3,
                        name = "이상민",
                        gender = "남",
                        birth = "640630",
                        phoneNumber = "01012345678"
                    ),
                    ReservationDetails(
                        reservationId = 1000,
                        date = Date(),
                        transportation = "택시",
                        message = "없음",
                        departureLocation = "부산 남구",
                        arrivalLocation = "부산대학교 병원"
                    )
                )
            )
        )
    }

    /* 리사이클러 뷰의 요소가 없을 때, 타이틀도 보이지 않게 하기 위한 함수 */
    private fun setAdapterDataObserver(adapter: ReservationApplyRecyclerViewAdapter) {
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
        })
    }

    private fun checkEmptyList(adapter: ReservationApplyRecyclerViewAdapter) {
        if (adapter.itemCount == 0) {
            binding.reservationApplyTextView.visibility = View.GONE
        } else {
            binding.reservationApplyTextView.visibility = View.VISIBLE
        }
    }

    private fun createMock(): List<ReservationInfo> =
        listOf(
            ReservationInfo(
                UserInfo(
                    id = 1,
                    name = "홍길동",
                    gender = "남",
                    birth = "640630",
                    phoneNumber = "01012345678"
                ),
                ReservationDetails(
                    reservationId = 2000,
                    date = Date(),
                    transportation = "택시",
                    message = "없음",
                    departureLocation = "부산 남구",
                    arrivalLocation = "부산대학교 병원"
                )
            ),
            ReservationInfo(
                UserInfo(
                    id = 2,
                    name = "강만기",
                    gender = "남",
                    birth = "640630",
                    phoneNumber = "01012345678"
                ),
                ReservationDetails(
                    reservationId = 3000,
                    date = Date(),
                    transportation = "택시",
                    message = "없음",
                    departureLocation = "부산 남구",
                    arrivalLocation = "부산대학교 병원"
                )
            )
        )

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}