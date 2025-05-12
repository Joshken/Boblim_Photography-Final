package com.example.boblim_photographyfinal

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import androidx.cardview.widget.CardView

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Show user info if provided
        val firstName = intent.getStringExtra("first_name") ?: ""
        val lastName = intent.getStringExtra("last_name") ?: ""
        val contactNumber = intent.getStringExtra("contact_number") ?: ""
        val selectedDate = intent.getStringExtra("selected_date") ?: ""
        val selectedTime = intent.getStringExtra("selected_time") ?: ""
        if (firstName.isNotEmpty() || lastName.isNotEmpty() || contactNumber.isNotEmpty()) {
        }

        // Set up click listeners for bottom navigation
        findViewById<LinearLayout>(R.id.calendarLayout).setOnClickListener {
            // Already in CalendarActivity, no need to start new activity
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

        findViewById<LinearLayout>(R.id.settingsLayout).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
            finish()
        }

        // Demo data: schedules for some dates
        val schedules = mapOf(
            "2024-05-04" to listOf("10:00 AM - Wedding Shoot", "2:00 PM - Birthday Party"),
            "2024-05-05" to listOf("1:00 PM - Graduation Photoshoot")
        )

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val scheduleContainer = findViewById<FrameLayout>(R.id.scheduleContainer)

        fun showSchedulesForDate(dateStr: String, schedules: List<String>?) {
            scheduleContainer.removeAllViews()
            val inflater = LayoutInflater.from(this)
            if (schedules != null && schedules.isNotEmpty()) {
                val card = inflater.inflate(R.layout.item_schedule_card, scheduleContainer, false) as CardView
                val title = card.findViewById<TextView>(R.id.scheduleTitle)
                val list = card.findViewById<TextView>(R.id.scheduleList)
                title.text = "Schedules for $dateStr"
                list.text = schedules.joinToString("\n")
                scheduleContainer.addView(card)
            } else {
                val card = inflater.inflate(R.layout.item_no_schedule_card, scheduleContainer, false) as CardView
                val bookNowBtn = card.findViewById<MaterialButton>(R.id.bookNowButton)
                bookNowBtn.setOnClickListener {
                    startActivity(Intent(this, CreateScheduleActivity::class.java))
                }
                scheduleContainer.addView(card)
            }
        }

        // Show today's schedule by default
        val today = dateFormat.format(Date(calendarView.date))
        showSchedulesForDate(today, schedules[today])

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val dateStr = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth)
            showSchedulesForDate(dateStr, schedules[dateStr])
        }
    }
} 