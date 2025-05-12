package com.example.boblim_photographyfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AddOnsAdapter(
    private val addOns: List<AddOn>,
    private val onQuantityChanged: ((AddOn) -> Unit)? = null,
    private val readOnly: Boolean = false
) : RecyclerView.Adapter<AddOnsAdapter.AddOnViewHolder>() {

    class AddOnViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.addonNameText)
        val priceText: TextView = view.findViewById(R.id.addonPriceText)
        val quantityText: TextView = view.findViewById(R.id.quantityText)
        val minusButton: ImageButton = view.findViewById(R.id.minusButton)
        val plusButton: ImageButton = view.findViewById(R.id.plusButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddOnViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_addon, parent, false)
        return AddOnViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddOnViewHolder, position: Int) {
        val addOn = addOns[position]
        holder.nameText.text = addOn.name
        holder.priceText.text = "₱${addOn.price}"
        holder.quantityText.text = addOn.quantity.toString()

        if (readOnly) {
            holder.minusButton.visibility = View.GONE
            holder.plusButton.visibility = View.GONE
        } else {
            holder.minusButton.visibility = View.VISIBLE
            holder.plusButton.visibility = View.VISIBLE
            holder.minusButton.setOnClickListener {
                if (addOn.quantity > 0) {
                    addOn.quantity--
                    holder.quantityText.text = addOn.quantity.toString()
                    onQuantityChanged?.invoke(addOn)
                }
            }
            holder.plusButton.setOnClickListener {
                addOn.quantity++
                holder.quantityText.text = addOn.quantity.toString()
                onQuantityChanged?.invoke(addOn)
            }
        }
    }

    override fun getItemCount() = addOns.size
} 