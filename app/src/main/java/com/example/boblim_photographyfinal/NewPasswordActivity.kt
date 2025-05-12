package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewPasswordActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        // Initialize views
        backButton = findViewById(R.id.backButton)
        newPasswordEditText = findViewById(R.id.newPasswordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText)
        submitButton = findViewById(R.id.submitButton)

        // Set click listeners
        backButton.setOnClickListener {
            finish()
        }

        submitButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            
            if (validatePasswords(newPassword, confirmPassword)) {
                // TODO: Implement actual password update logic here
                Toast.makeText(this, "Password updated successfully!", Toast.LENGTH_SHORT).show()
                
                // Navigate to Login
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validatePasswords(newPassword: String, confirmPassword: String): Boolean {
        if (newPassword.isEmpty()) {
            newPasswordEditText.error = "New password is required"
            return false
        }

        if (newPassword.length < 6) {
            newPasswordEditText.error = "Password must be at least 6 characters"
            return false
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordEditText.error = "Please confirm your password"
            return false
        }

        if (newPassword != confirmPassword) {
            confirmPasswordEditText.error = "Passwords do not match"
            return false
        }

        return true
    }
} 