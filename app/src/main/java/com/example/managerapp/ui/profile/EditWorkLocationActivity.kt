package com.example.managerapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.managerapp.databinding.ActivityEditWorkLocationBinding

class EditWorkLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditWorkLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditWorkLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToPrevious()
        navigateToEditWorkTimeActivity()
    }

    private fun navigateToEditWorkTimeActivity() {
        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, EditWorkTimeActivity::class.java)
            startActivity(intent)
        }
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