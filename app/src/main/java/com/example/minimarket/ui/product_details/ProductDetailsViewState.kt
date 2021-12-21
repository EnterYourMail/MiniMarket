package com.example.minimarket.ui.product_details

import com.example.minimarket.database.ProductDTO

data class ProductDetailsViewState(
    val productDTO: ProductDTO,
    val quantity: Int
)