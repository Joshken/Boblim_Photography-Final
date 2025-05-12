package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.boblim_photographyfinal.databinding.ActivityForgotNewPasswordBinding

class ForgotNewPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPasswordValidation()
        setupClickListeners()
    }

    private fun setupPasswordValidation() {
        binding.newPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkPasswordRequirements(s.toString())
            }
        })

        binding.confirmPasswordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkPasswordMatch(s.toString())
            }
        })
    }

    private fun checkPasswordRequirements(password: String) {
        // Check length requirement
        val hasMinLength = password.length >= 8
        binding.requirement1Check.visibility = if (hasMinLength) View.VISIBLE else View.INVISIBLE

        // Check uppercase requirement
        val hasUppercase = password.matches(Regex(".*[A-Z].*"))
        binding.requirement2Check.visibility = if (hasUppercase) View.VISIBLE else View.INVISIBLE

        // Check number requirement
        val hasNumber = password.matches(Regex(".*\\d.*"))
        binding.requirement3Check.visibility = if (hasNumber) View.VISIBLE else View.INVISIBLE

        // Enable/disable reset button based on all requirements
        val allRequirementsMet = hasMinLength && hasUppercase && hasNumber
        binding.resetPasswordButton.isEnabled = allRequirementsMet && 
            password == binding.confirmPasswordEditText.text.toString()
    }

    private fun checkPasswordMatch(confirmPassword: String) {
        val password = binding.newPasswordEditText.text.toString()
        if (confirmPassword.isNotEmpty()) {
            if (password == confirmPassword) {
                binding.confirmPasswordLayout.error = null
                binding.resetPasswordButton.isEnabled = isPasswordValid(password)
            } else {
                binding.confirmPasswordLayout.error = "Passwords do not match"
                binding.resetPasswordButton.isEnabled = false
            }
        } else {
            binding.confirmPasswordLayout.error = null
            binding.resetPasswordButton.isEnabled = false
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8 &&
               password.matches(Regex(".*[A-Z].*")) &&
               password.matches(Regex(".*\\d.*"))
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            finish()
        }

        binding.resetPasswordButton.setOnClickListener {
            val newPassword = binding.newPasswordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (validatePasswords(newPassword, confirmPassword)) {
                // TODO: Implement actual password reset logic here
                Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show()
                
                // Navigate back to login screen
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validatePasswords(newPassword: String, confirmPassword: String): Boolean {
        if (newPassword.isEmpty()) {
            binding.newPasswordLayout.error = "New password is required"
            return false
        }

        if (!isPasswordValid(newPassword)) {
            binding.newPasswordLayout.error = "Password does not meet requirements"
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordLayout.error = "Please confirm your password"
            return false
        }

        if (newPassword != confirmPassword) {
            binding.confirmPasswordLayout.error = "Passwords do not match"
            return false
        }

        return true
    }
} 