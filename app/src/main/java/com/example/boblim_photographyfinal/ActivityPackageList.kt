package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.content.DialogInterface
import android.widget.LinearLayout

class ActivityPackageList : AppCompatActivity() {
    private lateinit var filterGroup: RadioGroup
    private lateinit var packageTypeText: TextView
    private var currentCategory = "All"
    private var currentPackageType = "Search Packages"
    private val allPackages = mutableListOf<PackageInfo>()

    data class PackageInfo(
        val id: Int,
        val name: String,
        val price: Int,
        val description: String,
        val category: String
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_list)

        // Initialize packageTypeText before use
        packageTypeText = findViewById(R.id.packageTypeText)


        // Populate packages
        populatePackages()

        // Set up package type dropdown
        packageTypeText.setOnClickListener {
            showPackageTypeDialog()
        }


        // Set up back button
        findViewById<LinearLayout>(R.id.backButton).setOnClickListener {
            finish()
        }

        // Set up card click listeners to open package activities
        findViewById<CardView>(R.id.packageACard)?.setOnClickListener {
            val intent = Intent(this, ActivityPackageA::class.java)
            startActivityForResult(intent, 1)
        }
        findViewById<CardView>(R.id.packageBCard)?.setOnClickListener {
            val intent = Intent(this, ActivityPackageB::class.java)
            startActivityForResult(intent, 1)
        }
        findViewById<CardView>(R.id.packageCCard)?.setOnClickListener {
            val intent = Intent(this, ActivityPackageC::class.java)
            startActivityForResult(intent, 1)
        }
        findViewById<CardView>(R.id.packageDCard)?.setOnClickListener {
            val intent = Intent(this, ActivityPackageD::class.java)
            startActivityForResult(intent, 1)
        }
        findViewById<CardView>(R.id.packageECard)?.setOnClickListener {
            val intent = Intent(this, ActivityPackageE::class.java)
            startActivityForResult(intent, 1)
        }
        findViewById<CardView>(R.id.packageFCard)?.setOnClickListener {
            val intent = Intent(this, ActivityPackageF::class.java)
            startActivityForResult(intent, 1)
        }
        findViewById<CardView>(R.id.packageMCard)?.setOnClickListener {
            val intent = Intent(this, ActivityPackageM::class.java)
            startActivityForResult(intent, 1)
        }
        findViewById<CardView>(R.id.packageNCard)?.setOnClickListener {
            val intent = Intent(this, ActivityPackageN::class.java)
            startActivityForResult(intent, 1)
        }
    }

    private fun showPackageTypeDialog() {
        val packageTypes = resources.getStringArray(R.array.package_types)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Package Type")
        builder.setItems(packageTypes) { dialog: DialogInterface, which: Int ->
            currentPackageType = packageTypes[which]
            packageTypeText.text = currentPackageType
            filterAndShowPackages()
        }
        builder.show()
    }

    private fun filterAndShowPackages() {
        val filtered = allPackages.filter {
            val matchesCategory = currentCategory == "All" || it.category == currentCategory
            val matchesSearch = true // No searchEditText, so always true
            val matchesPackageType = when (currentPackageType) {
                "Search Packages" -> true
                "BASIC PACKAGES" -> it.category == "Basic"
                "BASIC + CAP PACKAGES" -> it.category == "Basic + Cap"
                "3-N-1 FRAME PACKAGES" -> it.category == "With Frame"
                else -> it.name == currentPackageType.split(" - ")[0]
            }
            matchesCategory && matchesSearch && matchesPackageType
        }

        // Show/hide package cards based on filtering
        filtered.forEach { packageInfo ->
            findViewById<CardView>(packageInfo.id)?.visibility = CardView.VISIBLE
        }
        allPackages.filter { it !in filtered }.forEach { packageInfo ->
            findViewById<CardView>(packageInfo.id)?.visibility = CardView.GONE
        }
    }

    private fun populatePackages() {
        // BASIC
        allPackages.add(PackageInfo(
            R.id.packageACard,
            "PACKAGE A",
            1300,
            "3 poses (toga, executive, barong/filipiniana)\n1-11R toga\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R executive\n2-2R executive\n1-8R barong/filipiniana\n2-2R barong/filipiniana",
            "Basic"
        ))
        allPackages.add(PackageInfo(
            R.id.packageBCard,
            "PACKAGE B",
            1095,
            "2 poses (toga, executive or barong/filipiniana)\n1-11R toga\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R executive or barong/filipiniana\n2-2R executive or barong/filipiniana",
            "Basic"
        ))
        allPackages.add(PackageInfo(
            R.id.packageCCard,
            "PACKAGE C",
            895,
            "1 pose (toga)\n1-8R toga\n4-2R toga\n2-2R toga black & white\n4-2x2 toga\n8-1x1 toga",
            "Basic"
        ))
        // BASIC + CAP
        allPackages.add(PackageInfo(
            R.id.packageDCard,
            "PACKAGE D",
            1550,
            "4 poses (toga, executive, barong/filipiniana)\n1-11R toga\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R toga with cap\n2-2R toga with cap\n1-8R executive\n2-2R executive\n1-8R barong/filipiniana\n2-2R barong/filipiniana",
            "Basic + Cap"
        ))
        allPackages.add(PackageInfo(
            R.id.packageECard,
            "PACKAGE E",
            1345,
            "3 poses (toga, executive or barong/filipiniana)\n1-11R toga\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R toga with cap\n2-2R toga with cap\n1-8R executive or barong/filipiniana\n2-2R executive or barong/filipiniana",
            "Basic + Cap"
        ))
        allPackages.add(PackageInfo(
            R.id.packageFCard,
            "PACKAGE F",
            1145,
            "2 poses (toga)\n1-8R toga\n4-2R toga\n2-2R toga black & white\n4-2x2 toga\n8-1x1 toga\n1-8R toga with cap\n2-2R toga with cap",
            "Basic + Cap"
        ))
        // WITH FRAME
        allPackages.add(PackageInfo(
            R.id.packageMCard,
            "PACKAGE M",
            2000,
            "3 poses (toga, executive, barong/filipiniana)\n1-11R toga with BASIC BLACK FRAME\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R executive\n2-2R executive\n1-8R barong/filipiniana\n2-2R barong/filipiniana",
            "With Frame"
        ))
        allPackages.add(PackageInfo(
            R.id.packageNCard,
            "PACKAGE N",
            1800,
            "2 poses (toga, executive or barong/filipiniana)\n1-11R toga with BASIC BLACK FRAME\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R executive or barong/filipiniana\n2-2R executive or barong/filipiniana",
            "With Frame"
        ))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            setResult(RESULT_OK, data)
            finish()
        }
    }
} 