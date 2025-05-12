package com.example.boblim_photographyfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton

class MalicayDeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_malicay_developer)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
} 