package com.example.boblim_photographyfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton

class BaguioDeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_baguio_developer)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
} 