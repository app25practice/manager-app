package com.example.managerapp.ui.companion

import LiveCompanionRecyclerViewAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managerapp.R
import com.example.managerapp.databinding.ActivityLiveCompanionBinding
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.label.LabelManager
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import java.text.SimpleDateFormat
import java.util.Locale

class LiveCompanionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLiveCompanionBinding
    private var adapter = LiveCompanionRecyclerViewAdapter()

    private var samplePatientName = "이수홍"
    private var sampleLatitude = 37.55
    private var sampleLongitude = 126.98

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLiveCompanionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMapView()
        setManagerStatus()
        setLiveCompanionRecyclerViewAdapter()
        setCompanionStatusInputFormClickListener()
        navigateToPrevious()
    }

    private fun startMapView() {
        binding.mapView.start(
            createMapLifeCycleCallback(),
            createMapReadyCallback(),
        )
    }

    private fun setManagerStatus() {
        binding.patientNameTextView.text = samplePatientName
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