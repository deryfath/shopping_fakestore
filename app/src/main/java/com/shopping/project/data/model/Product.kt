package com.shopping.project.data.model

data class Product(
    val productId: Int,
    val quantity: Int,
    var image: String? = "",
    var price: Double? = 0.0,
    var title: String? = ""
)