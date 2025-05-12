package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.content.DialogInterface
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView

class ListActivity : AppCompatActivity() {
    private lateinit var packagesAdapter: PackagesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var packageTypeText: TextView
    private val allPackages = mutableListOf<PhotoPackage>()
    private var currentCategory = "All"
    private var currentPackageType = "Search Packages"
    private lateinit var packageACard: CardView
    private lateinit var packageBCard: CardView
    private lateinit var packageCCard: CardView
    private lateinit var packageDCard: CardView
    private lateinit var packageECard: CardView
    private lateinit var packageFCard: CardView
    private lateinit var packageMCard: CardView
    private lateinit var packageNCard: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_list)

        // Initialize CardViews
        packageACard = findViewById(R.id.packageACard)
        packageBCard = findViewById(R.id.packageBCard)
        packageCCard = findViewById(R.id.packageCCard)
        packageDCard = findViewById(R.id.packageDCard)
        packageECard = findViewById(R.id.packageECard)
        packageFCard = findViewById(R.id.packageFCard)
        packageMCard = findViewById(R.id.packageMCard)
        packageNCard = findViewById(R.id.packageNCard)

        // Set up click listeners for bottom navigation
        findViewById<LinearLayout>(R.id.calendarLayout).setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
            finish()
        }

        findViewById<LinearLayout>(R.id.listLayout).setOnClickListener {
            // Already in ListActivity, no need to start new activity
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

        // Set up back button to return to CreateScheduleActivity
        findViewById<LinearLayout>(R.id.backButton).setOnClickListener {
            finish()
        }

        // Setup package type dropdown
        packageTypeText = findViewById(R.id.packageTypeText)
        packageTypeText.setOnClickListener {
            showPackageTypeDialog()
        }

        // Show all cards by default
        filterStaticCards("Search Packages")

        // Populate allPackages with all packages from the images
        populatePackages()
    }

    private fun showPackageTypeDialog() {
        val packageTypes = resources.getStringArray(R.array.package_types)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Package Type")
        builder.setItems(packageTypes) { dialog: DialogInterface, which: Int ->
            currentPackageType = packageTypes[which]
            packageTypeText.text = currentPackageType
            filterStaticCards(currentPackageType)
        }
        builder.show()
    }

    private fun filterStaticCards(type: String) {
        // Show/hide cards based on type
        when (type) {
            "Search Packages" -> {
                packageACard.visibility = View.VISIBLE
                packageBCard.visibility = View.VISIBLE
                packageCCard.visibility = View.VISIBLE
                packageDCard.visibility = View.VISIBLE
                packageECard.visibility = View.VISIBLE
                packageFCard.visibility = View.VISIBLE
                packageMCard.visibility = View.VISIBLE
                packageNCard.visibility = View.VISIBLE
            }
            "BASIC PACKAGES" -> {
                packageACard.visibility = View.VISIBLE
                packageBCard.visibility = View.VISIBLE
                packageCCard.visibility = View.VISIBLE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.GONE
            }
            "BASIC + CAP PACKAGES" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.VISIBLE
                packageECard.visibility = View.VISIBLE
                packageFCard.visibility = View.VISIBLE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.GONE
            }
            "3-N-1 FRAME PACKAGES" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.VISIBLE
                packageNCard.visibility = View.VISIBLE
            }
            "PACKAGE A - ₱1,300" -> {
                packageACard.visibility = View.VISIBLE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.GONE
            }
            "PACKAGE B - ₱1,095" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.VISIBLE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.GONE
            }
            "PACKAGE C - ₱895" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.VISIBLE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.GONE
            }
            "PACKAGE D - ₱1,550" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.VISIBLE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.GONE
            }
            "PACKAGE E - ₱1,345" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.VISIBLE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.GONE
            }
            "PACKAGE F - ₱1,145" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.VISIBLE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.GONE
            }
            "PACKAGE M - ₱2,300" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.VISIBLE
                packageNCard.visibility = View.GONE
            }
            "PACKAGE N - ₱2,500" -> {
                packageACard.visibility = View.GONE
                packageBCard.visibility = View.GONE
                packageCCard.visibility = View.GONE
                packageDCard.visibility = View.GONE
                packageECard.visibility = View.GONE
                packageFCard.visibility = View.GONE
                packageMCard.visibility = View.GONE
                packageNCard.visibility = View.VISIBLE
            }
        }
    }

    private fun populatePackages() {
        // BASIC
        allPackages.add(PhotoPackage(
            "PACKAGE A", 1300,
            "3 poses (toga, executive, barong/filipiniana)\n1-11R toga\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R executive\n2-2R executive\n1-8R barong/filipiniana\n2-2R barong/filipiniana",
            "Basic"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE B", 1095,
            "2 poses (toga, executive or barong/filipiniana)\n1-11R toga\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R executive or barong/filipiniana\n2-2R executive or barong/filipiniana",
            "Basic"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE C", 895,
            "1 pose (toga)\n1-8R toga\n4-2R toga\n2-2R toga black & white\n4-2x2 toga\n8-1x1 toga",
            "Basic"
        ))
        // BASIC + CAP
        allPackages.add(PhotoPackage(
            "PACKAGE D", 1550,
            "4 poses (toga, executive, barong/filipiniana)\n1-11R toga\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R toga with cap\n2-2R toga with cap\n1-8R executive\n2-2R executive\n1-8R barong/filipiniana\n2-2R barong/filipiniana",
            "Basic + Cap"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE E", 1345,
            "3 poses (toga, executive or barong/filipiniana)\n1-11R toga\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R toga with cap\n2-2R toga with cap\n1-8R executive or barong/filipiniana\n2-2R executive or barong/filipiniana",
            "Basic + Cap"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE F", 1145,
            "2 poses (toga)\n1-8R toga\n4-2R toga\n2-2R toga black & white\n4-2x2 toga\n8-1x1 toga\n1-8R toga with cap\n2-2R toga with cap",
            "Basic + Cap"
        ))
        // WITH FRAME
        allPackages.add(PhotoPackage(
            "PACKAGE G", 2000,
            "3 poses (toga, executive, barong/filipiniana)\n1-11R toga with BASIC BLACK FRAME\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R executive\n2-2R executive\n1-8R barong/filipiniana\n2-2R barong/filipiniana",
            "With Frame"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE H", 1800,
            "2 poses (toga, executive or barong/filipiniana)\n1-11R toga with BASIC BLACK FRAME\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R executive or barong/filipiniana\n2-2R executive or barong/filipiniana",
            "With Frame"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE I", 1200,
            "1 pose (toga)\n1-8R toga with BASIC BLACK FRAME\n4-2R toga\n2-2R toga black & white\n4-2x2 toga\n8-1x1 toga",
            "With Frame"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE J", 2250,
            "4 poses (toga, executive, barong/filipiniana)\n1-11R toga with BASIC BLACK FRAME\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R toga with cap\n2-2R toga with cap\n1-8R executive\n2-2R executive\n1-8R barong/filipiniana\n2-2R barong/filipiniana",
            "With Frame"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE K", 2050,
            "3 poses (toga, executive, barong/filipiniana)\n1-11R toga with BASIC BLACK FRAME\n4-2R toga\n2-2R toga black & white\n5-2x2 toga\n4-1x1 toga\n1-8R toga with cap\n2-2R toga with cap\n1-8R executive or barong/filipiniana\n2-2R executive or barong/filipiniana",
            "With Frame"
        ))
        allPackages.add(PhotoPackage(
            "PACKAGE L", 1450,
            "2 poses (toga)\n1-8R toga with BASIC BLACK FRAME\n4-2R toga\n2-2R toga black & white\n4-2x2 toga\n8-1x1 toga\n1-8R toga with cap\n2-2R toga with cap",
            "With Frame"
        ))
    }
} 