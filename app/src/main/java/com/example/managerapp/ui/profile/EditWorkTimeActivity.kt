package com.example.managerapp.ui.profile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.managerapp.databinding.ActivityEditWorkTimeBinding

class EditWorkTimeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditWorkTimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditWorkTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
    }

    private fun navigateToPrevious() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.previousBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}