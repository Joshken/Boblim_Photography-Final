package com.example.boblim_photographyfinal

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatActivity.RESULT_OK
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.appcompat.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat

class CreateScheduleActivity : AppCompatActivity() {
    private val selectedPackages = mutableListOf<PhotoPackage>()
    private val selectedAddOns = mutableListOf<AddOn>()
    private val PACKAGE_REQUEST_CODE = 1
    private val ADDONS_REQUEST_CODE = 2
    private lateinit var packageRecyclerView: RecyclerView
    private lateinit var packagesAdapter: PackagesAdapter
    private lateinit var selectedAddOnsRecyclerView: RecyclerView
    private lateinit var addOnsAdapter: AddOnsAdapter

    private fun updateBalance() {
        val downpaymentInput = findViewById<EditText>(R.id.downpaymentInput)
        val discountInput = findViewById<EditText>(R.id.discountInput)
        val balanceInput = findViewById<EditText>(R.id.balanceInput)
        val breakdownTotal = findViewById<TextView>(R.id.breakdownTotal)
        val breakdownDiscount = findViewById<TextView>(R.id.breakdownDiscount)
        val breakdownDownpayment = findViewById<TextView>(R.id.breakdownDownpayment)
        val breakdownBalance = findViewById<TextView>(R.id.breakdownBalance)

        val total = selectedPackages.sumOf { it.price * it.quantity } + selectedAddOns.sumOf { it.price * it.quantity }
        val discount = discountInput.text.toString().toIntOrNull() ?: 0
        val downpayment = downpaymentInput.text.toString().toIntOrNull() ?: 0
        val balance = total - discount - downpayment

        breakdownTotal.text = "Total: ₱$total"
        breakdownDiscount.text = "Discount: ₱$discount"
        breakdownDownpayment.text = "Downpayment: ₱$downpayment"
        breakdownBalance.text = "Balance: ₱$balance"
        balanceInput.setText(balance.toString())
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_schedule)

        val yearText = findViewById<TextView>(R.id.yearText)
        val dateText = findViewById<TextView>(R.id.dateText)
        val dateContainer = findViewById<LinearLayout>(R.id.dateContainer)
        val calendar = java.util.Calendar.getInstance()
        val timeTextView = findViewById<TextView>(R.id.timeText)

        fun updateDateViews(year: Int, month: Int, day: Int) {
            yearText.text = year.toString()
            val monthName = android.text.format.DateFormat.format("MMMM", java.util.GregorianCalendar(year, month, day)).toString()
            dateText.text = String.format("%s %02d", monthName, day)
        }

        // Show DatePickerDialog when dateContainer is clicked
        dateContainer.setOnClickListener {
            val y = calendar.get(java.util.Calendar.YEAR)
            val m = calendar.get(java.util.Calendar.MONTH)
            val d = calendar.get(java.util.Calendar.DAY_OF_MONTH)
            val dpd = android.app.DatePickerDialog(this, { _, year, month, dayOfMonth ->
                updateDateViews(year, month, dayOfMonth)
                calendar.set(year, month, dayOfMonth)
                // Show TimePickerDialog after date is picked
                val hour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
                val minute = calendar.get(java.util.Calendar.MINUTE)
                android.app.TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                    calendar.set(java.util.Calendar.HOUR_OF_DAY, selectedHour)
                    calendar.set(java.util.Calendar.MINUTE, selectedMinute)
                    timeTextView.text = String.format("%02d:%02d", selectedHour, selectedMinute)
                }, hour, minute, true).show()
            }, y, m, d)
            dpd.show()
        }

        // Initialize with today's date
        updateDateViews(calendar.get(java.util.Calendar.YEAR), calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH))

        // Set up back button
        findViewById<LinearLayout>(R.id.backButton).setOnClickListener {
            finish()
        }

        val selectPackageButton = findViewById<Button>(R.id.selectPackageButton)
        val selectAddOnsButton = findViewById<Button>(R.id.selectAddOnsButton)
        packageRecyclerView = findViewById(R.id.packageRecyclerView)
        packageRecyclerView.layoutManager = LinearLayoutManager(this)
        packagesAdapter = PackagesAdapter(
            selectedPackages,
            onQuantityChanged = { photoPackage ->
                updateBalance()
            }
        )
        packageRecyclerView.adapter = packagesAdapter

        // Swipe actions for packages
        val packageTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val packageItem = selectedPackages[position]
                if (direction == ItemTouchHelper.RIGHT) {
                    // Delete
                    selectedPackages.removeAt(position)
                    packagesAdapter.notifyItemRemoved(position)
                    updateBalance()
                } else if (direction == ItemTouchHelper.LEFT) {
                    // Edit
                    val input = EditText(this@CreateScheduleActivity)
                    input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
                    input.setText(packageItem.quantity.toString())
                    androidx.appcompat.app.AlertDialog.Builder(this@CreateScheduleActivity)
                        .setTitle("Update Quantity")
                        .setView(input)
                        .setPositiveButton("Update") { _, _ ->
                            val newQty = input.text.toString().toIntOrNull()
                            if (newQty != null && newQty > 0) {
                                packageItem.quantity = newQty
                                packagesAdapter.notifyItemChanged(position)
                                updateBalance()
                            } else {
                                Toast.makeText(this@CreateScheduleActivity, "Invalid quantity", Toast.LENGTH_SHORT).show()
                                packagesAdapter.notifyItemChanged(position)
                            }
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            packagesAdapter.notifyItemChanged(position)
                        }
                        .setOnCancelListener {
                            packagesAdapter.notifyItemChanged(position)
                        }
                        .show()
                }
            }
            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                val iconSize = 32.dpToPx()
                val iconMargin = (itemView.height - iconSize) / 2
                val deleteIcon = ContextCompat.getDrawable(this@CreateScheduleActivity, R.drawable.ic_delete)
                val editIcon = ContextCompat.getDrawable(this@CreateScheduleActivity, R.drawable.ic_edit)
                if (dX > 0) { // Swiping right (Delete)
                    paint.color = Color.parseColor("#E53935")
                    c.drawRect(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left + dX, itemView.bottom.toFloat(), paint)
                    // Draw delete icon and text
                    deleteIcon?.let {
                        val iconTop = itemView.top + iconMargin
                        val iconLeft = itemView.left + iconMargin
                        it.setBounds(iconLeft, iconTop, iconLeft + iconSize, iconTop + iconSize)
                        it.draw(c)
                        paint.color = Color.WHITE
                        paint.textSize = 40f
                        c.drawText("DELETE", iconLeft + iconSize + 32f, iconTop + iconSize.toFloat(), paint)
                    }
                } else if (dX < 0) { // Swiping left (Edit)
                    paint.color = Color.parseColor("#1976D2")
                    c.drawRect(itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat(), paint)
                    // Draw edit icon and text
                    editIcon?.let {
                        val iconTop = itemView.top + iconMargin
                        val iconRight = itemView.right - iconMargin
                        it.setBounds(iconRight - iconSize, iconTop, iconRight, iconTop + iconSize)
                        it.draw(c)
                        paint.color = Color.WHITE
                        paint.textSize = 40f
                        c.drawText("EDIT", iconRight - iconSize - 120f, iconTop + iconSize.toFloat(), paint)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        packageTouchHelper.attachToRecyclerView(packageRecyclerView)

        selectPackageButton.setOnClickListener {
            val intent = Intent(this, ActivityPackageList::class.java)
            startActivityForResult(intent, PACKAGE_REQUEST_CODE)
        }

        selectAddOnsButton.setOnClickListener {
            val intent = Intent(this, AddOnsActivity::class.java)
            intent.putExtra("selected_addons", ArrayList(selectedAddOns))
            startActivityForResult(intent, ADDONS_REQUEST_CODE)
        }

        val downpaymentInput = findViewById<EditText>(R.id.downpaymentInput)
        val discountInput = findViewById<EditText>(R.id.discountInput)
        val balanceInput = findViewById<EditText>(R.id.balanceInput)
        val breakdownTotal = findViewById<TextView>(R.id.breakdownTotal)
        val breakdownDiscount = findViewById<TextView>(R.id.breakdownDiscount)
        val breakdownDownpayment = findViewById<TextView>(R.id.breakdownDownpayment)
        val breakdownBalance = findViewById<TextView>(R.id.breakdownBalance)

        val balanceWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateBalance()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
        downpaymentInput.addTextChangedListener(balanceWatcher)
        discountInput.addTextChangedListener(balanceWatcher)

        // Initial breakdown update
        updateBalance()

        // Set up continue button
        findViewById<Button>(R.id.continueButton).setOnClickListener {
            val downpaymentInput = findViewById<EditText>(R.id.downpaymentInput).text.toString()
            val balanceInput = findViewById<EditText>(R.id.balanceInput).text.toString()
            val firstName = findViewById<EditText>(R.id.firstNameInput).text.toString()
            val lastName = findViewById<EditText>(R.id.lastNameInput).text.toString()
            val contactNumber = findViewById<EditText>(R.id.contactNumberInput).text.toString()
            val dateText = findViewById<TextView>(R.id.dateText).text.toString()
            val timeText = findViewById<TextView>(R.id.timeText).text.toString()

            if (downpaymentInput.isNotEmpty() && balanceInput.isNotEmpty()
                && firstName.isNotEmpty() && lastName.isNotEmpty() && contactNumber.isNotEmpty()
                && dateText.isNotEmpty() && timeText != "Select Time") {
                PaymentMethodBottomSheetFragment { method ->
                    when (method) {
                        "Via Cashies" -> {
                            val calendarIntent = Intent(this, CalendarActivity::class.java)
                            calendarIntent.putExtra("booking_status", "UNPAID")
                            calendarIntent.putExtra("first_name", firstName)
                            calendarIntent.putExtra("last_name", lastName)
                            calendarIntent.putExtra("contact_number", contactNumber)
                            calendarIntent.putExtra("selected_date", dateText)
                            calendarIntent.putExtra("selected_time", timeText)
                            startActivity(calendarIntent)
                            finish()
                        }
                        "GPay" -> {
                            Toast.makeText(this, "GPay payment flow coming soon!", Toast.LENGTH_SHORT).show()
                        }
                        "Card" -> {
                            Toast.makeText(this, "Card payment flow coming soon!", Toast.LENGTH_SHORT).show()
                        }
                        "Walk-in" -> {
                            Toast.makeText(this, "Please pay at the studio on your appointment day.", Toast.LENGTH_LONG).show()
                            val calendarIntent = Intent(this, CalendarActivity::class.java)
                            calendarIntent.putExtra("booking_status", "UNPAID")
                            calendarIntent.putExtra("first_name", firstName)
                            calendarIntent.putExtra("last_name", lastName)
                            calendarIntent.putExtra("contact_number", contactNumber)
                            calendarIntent.putExtra("selected_date", dateText)
                            calendarIntent.putExtra("selected_time", timeText)
                            startActivity(calendarIntent)
                            finish()
                        }
                    }
                }.show(supportFragmentManager, "PaymentMethodBottomSheet")
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        selectedAddOnsRecyclerView = findViewById(R.id.selectedAddOnsRecyclerView)
        selectedAddOnsRecyclerView.layoutManager = LinearLayoutManager(this)
        addOnsAdapter = AddOnsAdapter(selectedAddOns, readOnly = true)
        selectedAddOnsRecyclerView.adapter = addOnsAdapter

        // Swipe actions for add-ons
        val addOnsTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val addOnItem = selectedAddOns[position]
                if (direction == ItemTouchHelper.RIGHT) {
                    // Delete
                    selectedAddOns.removeAt(position)
                    addOnsAdapter.notifyItemRemoved(position)
                    updateBalance()
                } else if (direction == ItemTouchHelper.LEFT) {
                    // Edit
                    val input = EditText(this@CreateScheduleActivity)
                    input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
                    input.setText(addOnItem.quantity.toString())
                    androidx.appcompat.app.AlertDialog.Builder(this@CreateScheduleActivity)
                        .setTitle("Update Quantity")
                        .setView(input)
                        .setPositiveButton("Update") { _, _ ->
                            val newQty = input.text.toString().toIntOrNull()
                            if (newQty != null && newQty > 0) {
                                addOnItem.quantity = newQty
                                addOnsAdapter.notifyItemChanged(position)
                                updateBalance()
                            } else {
                                Toast.makeText(this@CreateScheduleActivity, "Invalid quantity", Toast.LENGTH_SHORT).show()
                                addOnsAdapter.notifyItemChanged(position)
                            }
                        }
                        .setNegativeButton("Cancel") { _, _ ->
                            addOnsAdapter.notifyItemChanged(position)
                        }
                        .setOnCancelListener {
                            addOnsAdapter.notifyItemChanged(position)
                        }
                        .show()
                }
            }
            override fun onChildDraw(
                c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                val iconSize = 32.dpToPx()
                val iconMargin = (itemView.height - iconSize) / 2
                val deleteIcon = ContextCompat.getDrawable(this@CreateScheduleActivity, R.drawable.ic_delete)
                val editIcon = ContextCompat.getDrawable(this@CreateScheduleActivity, R.drawable.ic_edit)
                if (dX > 0) { // Swiping right (Delete)
                    paint.color = Color.parseColor("#E53935")
                    c.drawRect(itemView.left.toFloat(), itemView.top.toFloat(), itemView.left + dX, itemView.bottom.toFloat(), paint)
                    // Draw delete icon and text
                    deleteIcon?.let {
                        val iconTop = itemView.top + iconMargin
                        val iconLeft = itemView.left + iconMargin
                        it.setBounds(iconLeft, iconTop, iconLeft + iconSize, iconTop + iconSize)
                        it.draw(c)
                        paint.color = Color.WHITE
                        paint.textSize = 40f
                        c.drawText("DELETE", iconLeft + iconSize + 32f, iconTop + iconSize.toFloat(), paint)
                    }
                } else if (dX < 0) { // Swiping left (Edit)
                    paint.color = Color.parseColor("#1976D2")
                    c.drawRect(itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat(), paint)
                    // Draw edit icon and text
                    editIcon?.let {
                        val iconTop = itemView.top + iconMargin
                        val iconRight = itemView.right - iconMargin
                        it.setBounds(iconRight - iconSize, iconTop, iconRight, iconTop + iconSize)
                        it.draw(c)
                        paint.color = Color.WHITE
                        paint.textSize = 40f
                        c.drawText("EDIT", iconRight - iconSize - 120f, iconTop + iconSize.toFloat(), paint)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        })
        addOnsTouchHelper.attachToRecyclerView(selectedAddOnsRecyclerView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val paymentMethod = data.getStringExtra("payment_method")
            if (paymentMethod == "Via Cashies") {
                val calendarIntent = Intent(this, CalendarActivity::class.java)
                calendarIntent.putExtra("booking_status", "UNPAID")
                // Pass other booking details as needed
                startActivity(calendarIntent)
                finish()
            }
            // Handle other payment methods if needed
        }
        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                PACKAGE_REQUEST_CODE -> {
                    val name = data.getStringExtra("package_name") ?: return
                    val price = data.getIntExtra("package_price", 0)
                    // Check if the package already exists
                    val existingPackage = selectedPackages.find { it.name == name }
                    if (existingPackage != null) {
                        existingPackage.quantity++
                    } else {
                        selectedPackages.add(PhotoPackage(name, price, "", "", 1))
                    }
                    packagesAdapter.notifyDataSetChanged()
                    updateBalance()
                }
                ADDONS_REQUEST_CODE -> {
                    val totalPrice = data.getIntExtra("total_price", 0)
                    val addOns = data.getSerializableExtra("selected_addons") as? ArrayList<AddOn>
                    if (addOns != null) {
                        // Update or add new add-ons
                        for (addOn in addOns) {
                            val existingAddOn = selectedAddOns.find { it.name == addOn.name }
                            if (existingAddOn != null) {
                                existingAddOn.quantity = addOn.quantity
                            } else {
                                selectedAddOns.add(addOn)
                            }
                        }
                        // Remove add-ons that are no longer selected
                        selectedAddOns.removeAll { existing -> addOns.none { it.name == existing.name } }
                        addOnsAdapter.notifyDataSetChanged()
                        updateBalance()
                    }
                }
            }
        }
    }
} 