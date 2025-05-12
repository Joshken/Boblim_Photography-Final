package com.example.boblim_photographyfinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityPackageM : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_m)

        val chooseButton = findViewById<Button>(R.id.choosePackageButton)
        chooseButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("package_name", "PACKAGE M")
            resultIntent.putExtra("package_price", 2300)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
} 