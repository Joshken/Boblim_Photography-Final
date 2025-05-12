package com.example.boblim_photographyfinal

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog

class ChangeLanguagesActivity : AppCompatActivity() {
    private var selectedLang: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_languages)

        val englishOption = findViewById<LinearLayout>(R.id.english_option)
        val hindiOption = findViewById<LinearLayout>(R.id.hindi_option)
        val filipinoOption = findViewById<LinearLayout>(R.id.filipino_option)
        val urduOption = findViewById<LinearLayout>(R.id.urdu_option)
        val banglaOption = findViewById<LinearLayout>(R.id.bangla_option)
        val japaneseOption = findViewById<LinearLayout>(R.id.japanese_option)

        val englishCheck = findViewById<ImageView>(R.id.english_check)
        val hindiCheck = findViewById<ImageView>(R.id.hindi_check)
        val filipinoCheck = findViewById<ImageView>(R.id.filipino_check)
        val urduCheck = findViewById<ImageView>(R.id.urdu_check)
        val banglaCheck = findViewById<ImageView>(R.id.bangla_check)
        val japaneseCheck = findViewById<ImageView>(R.id.japanese_check)

        val confirmButton = findViewById<android.widget.Button>(R.id.confirmButton)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        // Helper to update checkmarks
        fun updateChecks(selected: String) {
            englishCheck.visibility = if (selected == "en") android.view.View.VISIBLE else android.view.View.GONE
            hindiCheck.visibility = if (selected == "hi") android.view.View.VISIBLE else android.view.View.GONE
            filipinoCheck.visibility = if (selected == "fil") android.view.View.VISIBLE else android.view.View.GONE
            urduCheck.visibility = if (selected == "ur") android.view.View.VISIBLE else android.view.View.GONE
            banglaCheck.visibility = if (selected == "bn") android.view.View.VISIBLE else android.view.View.GONE
            japaneseCheck.visibility = if (selected == "ja") android.view.View.VISIBLE else android.view.View.GONE
        }

        // Restore previously selected language
        val prefs = getSharedPreferences("profile", Context.MODE_PRIVATE)
        val savedLang = prefs.getString("language", "en") ?: "en"
        selectedLang = savedLang
        updateChecks(savedLang)
        val originalLang = savedLang

        englishOption.setOnClickListener {
            selectedLang = "en"
            updateChecks("en")
        }
        hindiOption.setOnClickListener {
            selectedLang = "hi"
            updateChecks("hi")
        }
        filipinoOption.setOnClickListener {
            selectedLang = "fil"
            updateChecks("fil")
        }
        urduOption.setOnClickListener {
            selectedLang = "ur"
            updateChecks("ur")
        }
        banglaOption.setOnClickListener {
            selectedLang = "bn"
            updateChecks("bn")
        }
        japaneseOption.setOnClickListener {
            selectedLang = "ja"
            updateChecks("ja")
        }

        backButton.setOnClickListener {
            handleBack(originalLang)
        }

        confirmButton.setOnClickListener {
            selectedLang?.let { langCode ->
                if (langCode != originalLang) {
                    AlertDialog.Builder(this)
                        .setTitle("Change language?")
                        .setMessage("Do you want to change language? This may affect the language of the application as the application is still on the beta test.")
                        .setPositiveButton("Yes") { _, _ ->
                            prefs.edit().putString("language", langCode).apply()
                            setLocale(langCode)
                            Toast.makeText(this, "Language changed!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .setNegativeButton("No", null)
                        .show()
                } else {
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
        val prefs = getSharedPreferences("profile", Context.MODE_PRIVATE)
        val originalLang = prefs.getString("language", "en") ?: "en"
        handleBack(originalLang)
    }

    private fun handleBack(originalLang: String) {
        if (selectedLang != originalLang) {
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

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
} 