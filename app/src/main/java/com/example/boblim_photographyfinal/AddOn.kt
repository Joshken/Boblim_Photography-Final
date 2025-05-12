package com.example.boblim_photographyfinal

data class AddOn(
    val name: String,
    val price: Int,
    var quantity: Int = 0
) : java.io.Serializable 