package com.shopping.project.data.model

data class CartListResponse(
    val date: String,
    val id: Int,
    val products: MutableList<Product>,
    val userId: Int
)