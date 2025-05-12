package com.example.boblim_photographyfinal

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener { finish() }

        val currentPasswordEditText = findViewById<TextInputEditText>(R.id.currentPasswordEditText)
        val newPasswordEditText = findViewById<TextInputEditText>(R.id.newPasswordEditText)
        val confirmPasswordEditText = findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val submitButton = findViewById<Button>(R.id.submitButton)

        // Ensure current password is hidden
        currentPasswordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        val lengthCheckmark = findViewById<ImageView>(R.id.lengthCheckmark)
        val letterCheckmark = findViewById<ImageView>(R.id.letterCheckmark)
        val specialCharCheckmark = findViewById<ImageView>(R.id.specialCharCheckmark)
        val passwordMatchText = findViewById<TextView>(R.id.passwordMatchText)

        // For demo: get the stored password from SharedPreferences
        val prefs = getSharedPreferences("profile", Context.MODE_PRIVATE)
        val storedPassword = prefs.getString("password", "password123") // default for demo

        fun validatePasswordFields(): Boolean {
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            var valid = true

            // Length check
            if (newPassword.length >= 10) {
                lengthCheckmark.visibility = ImageView.VISIBLE
            } else {
                lengthCheckmark.visibility = ImageView.INVISIBLE
                valid = false
            }
            // Letter check
            if (newPassword.any { it.isLetter() }) {
                letterCheckmark.visibility = ImageView.VISIBLE
            } else {
                letterCheckmark.visibility = ImageView.INVISIBLE
                valid = false
            }
            // Special char or digit check
            if (newPassword.any { it.isDigit() || !it.isLetterOrDigit() }) {
                specialCharCheckmark.visibility = ImageView.VISIBLE
            } else {
                specialCharCheckmark.visibility = ImageView.INVISIBLE
                valid = false
            }
            // Password match check
            if (newPassword.isNotEmpty() && newPassword == confirmPassword) {
                passwordMatchText.visibility = TextView.VISIBLE
            } else {
                passwordMatchText.visibility = TextView.GONE
                valid = false
            }
            return valid
        }

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                submitButton.isEnabled = validatePasswordFields()
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        newPasswordEditText.addTextChangedListener(watcher)
        confirmPasswordEditText.addTextChangedListener(watcher)

        submitButton.isEnabled = false

        submitButton.setOnClickListener {
            val currentPassword = currentPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (currentPassword != storedPassword) {
                Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (newPassword == currentPassword) {
                Toast.makeText(this, "New password must be different from current password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save new password
            prefs.edit().putString("password", newPassword).apply()
            Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_LONG).show()
            finish()
        }
    }
} 