package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Set up click listeners for bottom navigation
        findViewById<LinearLayout>(R.id.calendarLayout).setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.listLayout).setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.homeLayout).setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.profileLayout).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
            finish()
        }

        findViewById<RelativeLayout>(R.id.rlProfileCard).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.settingsLayout).setOnClickListener {
            // Already in SettingsActivity, no need to start new activity
        }

        findViewById<RelativeLayout>(R.id.rlDevelopers).setOnClickListener {
            startActivity(Intent(this, DevelopersPageActivity::class.java))
        }

        findViewById<RelativeLayout>(R.id.rlChangeLanguage).setOnClickListener {
            startActivity(Intent(this, ChangeLanguagesActivity::class.java))
        }

        findViewById<RelativeLayout>(R.id.rlChangePassword).setOnClickListener {
            startActivity(Intent(this, ChangePasswordActivity::class.java))
        }
    }
} 