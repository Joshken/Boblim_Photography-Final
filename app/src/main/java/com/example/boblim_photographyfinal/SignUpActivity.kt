package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.boblim_photographyfinal.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGenderSpinner()
        setupClickListeners()
    }

    private fun setupGenderSpinner() {
        val genders = arrayOf("Male", "Female", "Other", "Prefer not to say")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genders)
        binding.genderSpinner.setAdapter(adapter)
    }

    private fun setupClickListeners() {
        binding.signUpButton.setOnClickListener {
            val firstName = binding.firstNameEditText.text.toString()
            val middleInitial = binding.middleInitialEditText.text.toString()
            val lastName = binding.lastNameEditText.text.toString()
            val username = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            val location = binding.locationEditText.text.toString()
            val gender = binding.genderSpinner.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (validateInput(firstName, lastName, username, email, phone, gender, password, confirmPassword)) {
                // TODO: Implement actual sign up logic
                Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun validateInput(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        phone: String,
        gender: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (firstName.isEmpty()) {
            binding.firstNameEditText.error = "First name is required"
            return false
        }

        if (lastName.isEmpty()) {
            binding.lastNameEditText.error = "Last name is required"
            return false
        }

        if (username.isEmpty()) {
            binding.usernameEditText.error = "Username is required"
            return false
        }

        if (email.isEmpty()) {
            binding.emailEditText.error = "Email is required"
            return false
        }

        if (phone.isEmpty()) {
            binding.phoneEditText.error = "Phone number is required"
            return false
        }

        if (gender.isEmpty()) {
            binding.genderSpinner.error = "Gender is required"
            return false
        }

        if (password.isEmpty()) {
            binding.passwordEditText.error = "Password is required"
            return false
        }

        if (password.length < 6) {
            binding.passwordEditText.error = "Password must be at least 6 characters"
            return false
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordEditText.error = "Please confirm your password"
            return false
        }

        if (password != confirmPassword) {
            binding.confirmPasswordEditText.error = "Passwords do not match"
            return false
        }

        return true
    }
} 