package com.example.minimarket.ui.product_details

import com.example.minimarket.database.Product

data class ProductDetailsViewState(
    val product: Product,
    val quantity: Int
)