package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.boblim_photographyfinal.databinding.ActivityOtpConfirmationBinding

class OtpConfirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.verifyButton.setOnClickListener {
            val otp = binding.otpEditText.text.toString()
            
            if (validateOtp(otp)) {
                // TODO: Implement actual OTP verification logic here
                Toast.makeText(this, "OTP verified successfully!", Toast.LENGTH_SHORT).show()
                
                // Navigate to New Password screen for password reset flow
                if (intent.getBooleanExtra("isPasswordReset", false)) {
                    startActivity(Intent(this, ForgotNewPasswordActivity::class.java))
                    finish()
                } else {
                    // For regular signup flow, go to dashboard
                    startActivity(Intent(this, DashboardActivity::class.java))
                    finish()
                }
            }
        }

        binding.resendButton.setOnClickListener {
            // TODO: Implement resend OTP logic here
            Toast.makeText(this, "OTP resent!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateOtp(otp: String): Boolean {
        if (otp.isEmpty()) {
            binding.otpEditText.error = "OTP is required"
            return false
        }

        if (otp.length != 6) {
            binding.otpEditText.error = "Please enter a valid 6-digit OTP"
            return false
        }

        if (!otp.matches(Regex("\\d+"))) {
            binding.otpEditText.error = "OTP must contain only digits"
            return false
        }

        return true
    }
} 