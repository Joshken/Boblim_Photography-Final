package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.boblim_photographyfinal.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.backToLoginText.setOnClickListener {
            finish()
        }

        binding.resetButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            if (validateEmail(email)) {
                // TODO: Implement password reset logic
                val intent = Intent(this, OtpConfirmationActivity::class.java)
                intent.putExtra("isPasswordReset", true)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateEmail(email: String): Boolean {
        if (email.isEmpty()) {
            binding.emailLayout.error = "Email is required"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailLayout.error = "Please enter a valid email"
            return false
        }

        binding.emailLayout.error = null
        return true
    }
} 