package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddOnsActivity : AppCompatActivity() {
    private lateinit var addOnsAdapter: AddOnsAdapter
    private val addOns = mutableListOf<AddOn>()
    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ons)

        // Set up back button
        findViewById<LinearLayout>(R.id.backButton).setOnClickListener {
            finish()
        }

        // Initialize add-ons list
        initializeAddOns()

        // Restore previously selected add-ons' quantities
        val previouslySelected = intent.getSerializableExtra("selected_addons") as? ArrayList<AddOn>
        if (previouslySelected != null) {
            for (selected in previouslySelected) {
                val match = addOns.find { it.name == selected.name }
                if (match != null) {
                    match.quantity = selected.quantity
                }
            }
        }

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.addOnsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        addOnsAdapter = AddOnsAdapter(addOns, onQuantityChanged = { addOn ->
            updateTotalPrice()
        })
        recyclerView.adapter = addOnsAdapter

        // Set up proceed button
        findViewById<Button>(R.id.proceedButton).setOnClickListener {
            val selectedAddOns = addOns.filter { it.quantity > 0 }
            val intent = Intent().apply {
                putExtra("total_price", totalPrice)
                putExtra("selected_addons", ArrayList(selectedAddOns))
            }
            setResult(RESULT_OK, intent)
            finish()
        }

        updateTotalPrice()
    }

    private fun initializeAddOns() {
        // 8R Size Add-ons
        addOns.add(AddOn("8R - Extra Shot", 300))
        addOns.add(AddOn("8R - Barkada Shot", 300))
        addOns.add(AddOn("8R - Couple Shot", 300))

        // Wallet Size Add-ons
        addOns.add(AddOn("Wallet - Extra Shot", 350))
        addOns.add(AddOn("Wallet - Barkada Shot", 350))
        addOns.add(AddOn("Wallet - Couple Shot", 350))

        // Frame Add-ons
        addOns.add(AddOn("8R Old English Frame", 750))
        addOns.add(AddOn("1R Old English Frame", 950))
        addOns.add(AddOn("8R Black Frame", 300))
        addOns.add(AddOn("1R Black Frame", 600))
    }

    private fun updateTotalPrice() {
        totalPrice = addOns.sumOf { it.price * it.quantity }
        findViewById<TextView>(R.id.totalPriceText).text = "Total: ₱$totalPrice"
    }
} 