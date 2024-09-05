package com.example.managerapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.managerapp.R
import com.example.managerapp.databinding.ActivityLoginEntryBinding
import com.example.managerapp.ui.main.MainActivity
import com.example.managerapp.ui.register.RegisterEntryActivity

class LoginEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEntryBinding.inflate(layoutInflater)

        setContentView(binding.root)
        navigateToRegister()
        navigatoToMain()
    }

    private fun navigateToRegister() {
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this, RegisterEntryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigatoToMain() {
        binding.appNameTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}