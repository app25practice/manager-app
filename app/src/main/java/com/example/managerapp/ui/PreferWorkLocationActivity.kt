package com.example.managerapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.managerapp.R
import com.example.managerapp.databinding.ActivityPreferWorkLocationBinding

class PreferWorkLocationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityPreferWorkLocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferWorkLocationBinding.inflate(layoutInflater)
    }
}