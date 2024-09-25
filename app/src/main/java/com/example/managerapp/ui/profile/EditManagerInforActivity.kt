package com.example.managerapp.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.managerapp.databinding.ActivityEditManagerInforBinding

class EditManagerInforActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditManagerInforBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditManagerInforBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToEditWorkLocationActivity()

    }

    private fun navigateToEditWorkLocationActivity() {
        binding.editWorkTimeLocationBtn.setOnClickListener {
            val intent = Intent(this, EditWorkLocationActivity::class.java)
            startActivity(intent)
        }
    }
}