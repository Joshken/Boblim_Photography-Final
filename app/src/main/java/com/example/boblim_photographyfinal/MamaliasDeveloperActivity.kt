package com.example.boblim_photographyfinal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton

class MamaliasDeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mamalias_developer)

        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
} 