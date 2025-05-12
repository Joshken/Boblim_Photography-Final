package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class DevelopersPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers_page)

        findViewById<com.google.android.material.card.MaterialCardView>(R.id.lead_dev_card).setOnClickListener {
            startActivity(Intent(this, BaguioDeveloperActivity::class.java))
        }
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.team_member1_card).setOnClickListener {
            startActivity(Intent(this, MamaliasDeveloperActivity::class.java))
        }
        findViewById<com.google.android.material.card.MaterialCardView>(R.id.team_member2_card).setOnClickListener {
            startActivity(Intent(this, MalicayDeveloperActivity::class.java))
        }
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
} 