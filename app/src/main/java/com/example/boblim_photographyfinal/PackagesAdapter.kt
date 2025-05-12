package com.example.boblim_photographyfinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PackagesAdapter(
    private var packages: List<PhotoPackage>,
    private val onQuantityChanged: ((PhotoPackage) -> Unit)? = null
) : RecyclerView.Adapter<PackagesAdapter.PackageViewHolder>() {

    class PackageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val packageName: TextView = itemView.findViewById(R.id.packageName)
        val packagePrice: TextView = itemView.findViewById(R.id.packagePrice)
        val quantityText: TextView = itemView.findViewById(R.id.quantityText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_package, parent, false)
        return PackageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        val packageInfo = packages[position]
        holder.packageName.text = packageInfo.name
        holder.packagePrice.text = "₱${packageInfo.price}"
        holder.quantityText.text = "x${packageInfo.quantity}"
    }

    override fun getItemCount(): Int = packages.size

    fun updateList(newList: List<PhotoPackage>) {
        packages = newList
        notifyDataSetChanged()
    }
} 