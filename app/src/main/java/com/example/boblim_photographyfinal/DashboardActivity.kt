package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.example.boblim_photographyfinal.CalendarActivity
import com.example.boblim_photographyfinal.ListActivity
import com.example.boblim_photographyfinal.ProfileActivity
import com.example.boblim_photographyfinal.SettingsActivity

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Set up click listeners for bottom navigation
        findViewById<LinearLayout>(R.id.calendarLayout).setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.listLayout).setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.homeLayout).setOnClickListener {
            // Already in DashboardActivity, no need to start new activity
            // Just ensure we're at the top of the dashboard
            findViewById<ScrollView>(R.id.scrollView)?.smoothScrollTo(0, 0)
        }

        findViewById<LinearLayout>(R.id.profileLayout).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        findViewById<LinearLayout>(R.id.settingsLayout).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
} 