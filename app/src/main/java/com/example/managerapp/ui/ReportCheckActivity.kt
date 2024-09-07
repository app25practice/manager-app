package com.example.managerapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.managerapp.R
import com.example.managerapp.databinding.ActivityReportCheckBinding

class ReportCheckActivity : AppCompatActivity() {
    private lateinit var binding : ActivityReportCheckBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun completeWriteReport(){
        binding.nextBtn.setOnClickListener{
            // 저장
        }
    }
}