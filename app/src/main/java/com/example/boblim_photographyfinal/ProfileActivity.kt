package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

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
            // Already in ProfileActivity, no need to start new activity
        }

        findViewById<LinearLayout>(R.id.settingsLayout).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }

        findViewById<android.widget.Button>(R.id.btnEditProfile).setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }
    }
} 