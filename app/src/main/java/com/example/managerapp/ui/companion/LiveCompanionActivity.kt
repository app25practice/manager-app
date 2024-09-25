package com.example.managerapp.ui.companion

import LiveCompanionRecyclerViewAdapter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.managerapp.R
import com.example.managerapp.databinding.ActivityLiveCompanionBinding
import com.example.managerapp.ui.status.CompanionCompleteDialog
import com.example.managerapp.ui.status.data.ReservationDetails
import com.example.managerapp.ui.status.data.ReservationInfo
import com.example.managerapp.ui.status.data.UserInfo
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LiveCompanionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLiveCompanionBinding
    private var adapter = LiveCompanionRecyclerViewAdapter()

    private var sampleLatitude = 37.55
    private var sampleLongitude = 126.98

    // 테스트 용 예약 데이터
    private var mock = ReservationInfo(
        UserInfo(
            id = 7,
            name = "문경자",
            gender = "여",
            birth = "640630",
            phoneNumber = "01012345678"
        ),
        ReservationDetails(
            reservationId = 7000,
            date = Date(),
            transportation = "택시",
            message = "없음",
            departureLocation = "부산 남구",
            arrivalLocation = "부산대학교 병원"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveCompanionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMapView()
        setManagerStatus()
        setLiveCompanionRecyclerViewAdapter()
        setCompanionStatusInputFormClickListener()
        setCompanionCompleteBtnClickListener()
        navigateToPrevious()
    }

    private fun startMapView() {
        binding.mapView.start(
            createMapLifeCycleCallback(),
            createMapReadyCallback(),
        )
    }

    private fun setManagerStatus() {
        binding.patientNameTextView.text = mock.userInfo.name
    }

    private fun setCompanionStatusInputFormClickListener() {
        binding.companionStatusInputBtn.setOnClickListener {
            val formattedDate = getCurrentFormattedDateTime()
            val currentList = adapter.currentList.toMutableList()
            val message = formattedDate + " " + binding.companionStatusInputEditText.text

            currentList.add(message)
            adapter.submitList(currentList)

            binding.companionStatusInputEditText.text.clear()
        }
    }

    // 다이얼 로그에 예약 정보도 같이 전달
    private fun setCompanionCompleteBtnClickListener() {
        binding.completeCompanionBtn.setOnClickListener {
            val companionCompleteDialog = CompanionCompleteDialog(this, mock)
            companionCompleteDialog.show()
        }
    }

    private fun getCurrentFormattedDateTime(): String {
        val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yy.MM.dd HH:mm", Locale.KOREAN)

        return dateFormat.format(currentTime)
    }

    private fun createMapLifeCycleCallback(): MapLifeCycleCallback {
        return object : MapLifeCycleCallback() {
            override fun onMapDestroy() {}

            override fun onMapError(error: Exception?) {}
        }
    }

    private fun createMapReadyCallback(): KakaoMapReadyCallback {
        return object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                val labelManager = kakaoMap.labelManager
                if (labelManager != null) {
                    addLabelsToMap(labelManager)
                }
            }

            override fun getPosition(): LatLng {
                return LatLng.from(sampleLatitude, sampleLongitude)
            }
        }
    }

    private fun addLabelsToMap(labelManager: LabelManager) {
        val styles =
            LabelStyles.from(
                LabelStyle.from(R.drawable.marker).setZoomLevel(8),
            )

        val labelOptions =
            LabelOptions.from(
                LatLng.from(sampleLatitude, sampleLongitude),
            )
                .setStyles(styles)

        labelManager.layer?.addLabel(labelOptions)
    }

    private fun setLiveCompanionRecyclerViewAdapter() {
        binding.liveCompanionRecyclerView.adapter = adapter
        binding.liveCompanionRecyclerView.layoutManager = LinearLayoutManager(this)

        if (adapter.itemCount == 0)
            binding.liveCompanionRecyclerView.visibility = View.GONE

        setLiveCompanionAdapterDataObserver()
    }

    private fun setLiveCompanionAdapterDataObserver() {
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmptyList()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmptyList()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmptyList()
            }
        })
    }

    private fun checkEmptyList() {
        if (adapter.itemCount == 0) {
            binding.liveCompanionRecyclerView.visibility = View.GONE
        } else {
            binding.liveCompanionRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun navigateToPrevious() {
        binding.mapPreviousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @Override
    override fun onResume() {
        super.onResume()
        binding.mapView.resume()
    }

    @Override
    public override fun onPause() {
        super.onPause()
        binding.mapView.pause()
    }
}