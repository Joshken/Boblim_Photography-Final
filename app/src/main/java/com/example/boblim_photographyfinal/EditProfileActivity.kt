package com.example.boblim_photographyfinal

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val etFirstName = findViewById<TextInputEditText>(R.id.etFirstName)
        val etMiddleInitial = findViewById<TextInputEditText>(R.id.etMiddleInitial)
        val etLastName = findViewById<TextInputEditText>(R.id.etLastName)
        val etUsername = findViewById<TextInputEditText>(R.id.etUsername)
        val etEmail = findViewById<TextInputEditText>(R.id.etEmail)
        val etPhone = findViewById<TextInputEditText>(R.id.etPhone)
        val etLocation = findViewById<TextInputEditText>(R.id.etLocation)
        val genderSpinner = findViewById<AutoCompleteTextView>(R.id.genderSpinner)
        val btnSave = findViewById<MaterialButton>(R.id.btnSave)
        val btnCancel = findViewById<MaterialButton>(R.id.btnCancel)

        // Load existing data (if any)
        val prefs = getSharedPreferences("profile", Context.MODE_PRIVATE)
        etFirstName.setText(prefs.getString("firstName", ""))
        etMiddleInitial.setText(prefs.getString("middleInitial", ""))
        etLastName.setText(prefs.getString("lastName", ""))
        etUsername.setText(prefs.getString("username", ""))
        etEmail.setText(prefs.getString("email", ""))
        etPhone.setText(prefs.getString("phone", ""))
        etLocation.setText(prefs.getString("location", ""))
        genderSpinner.setText(prefs.getString("gender", ""), false)

        val genderOptions = listOf("Male", "Female", "Other")
        val genderAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderOptions)
        genderSpinner.setAdapter(genderAdapter)

        // Store original values
        val originalFirstName = etFirstName.text.toString()
        val originalMiddleInitial = etMiddleInitial.text.toString()
        val originalLastName = etLastName.text.toString()
        val originalUsername = etUsername.text.toString()
        val originalEmail = etEmail.text.toString()
        val originalPhone = etPhone.text.toString()
        val originalLocation = etLocation.text.toString()
        val originalGender = genderSpinner.text.toString()

        btnSave.setOnClickListener {
            val editor = prefs.edit()
            editor.putString("firstName", etFirstName.text.toString())
            editor.putString("middleInitial", etMiddleInitial.text.toString())
            editor.putString("lastName", etLastName.text.toString())
            editor.putString("username", etUsername.text.toString())
            editor.putString("email", etEmail.text.toString())
            editor.putString("phone", etPhone.text.toString())
            editor.putString("location", etLocation.text.toString())
            editor.putString("gender", genderSpinner.text.toString())
            editor.apply()
            Toast.makeText(this, "Profile updated!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnCancel.setOnClickListener {
            val changed =
                etFirstName.text.toString() != originalFirstName ||
                etMiddleInitial.text.toString() != originalMiddleInitial ||
                etLastName.text.toString() != originalLastName ||
                etUsername.text.toString() != originalUsername ||
                etEmail.text.toString() != originalEmail ||
                etPhone.text.toString() != originalPhone ||
                etLocation.text.toString() != originalLocation ||
                genderSpinner.text.toString() != originalGender

            if (changed) {
                AlertDialog.Builder(this)
                    .setTitle("Discard changes?")
                    .setMessage("You have unsaved changes. Are you sure you want to cancel?")
                    .setPositiveButton("Yes") { _, _ -> finish() }
                    .setNegativeButton("No", null)
                    .show()
            } else {
                finish()
            }
        }
    }
} 