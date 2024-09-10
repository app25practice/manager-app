package com.example.managerapp.ui.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.managerapp.R
import com.example.managerapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigateToCertificate()
    }

    private fun navigateToCertificate() {
        binding.goCertificationBtn.setOnClickListener {
            val intent = Intent(this, CertificateActivity::class.java)
            startActivity(intent)
        }
    }

}