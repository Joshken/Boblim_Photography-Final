package com.example.boblim_photographyfinal

data class PhotoPackage(
    val name: String,
    val price: Int,
    val description: String,
    val category: String, // e.g., "Basic", "Basic + Cap", "With Frame"
    var quantity: Int = 1
) : java.io.Serializable 