package com.example.minimarket.database

import androidx.room.Embedded
import androidx.room.Relation

data class ProductDetails(
    @Embedded
    val productDTO: ProductDTO,
    @Relation(parentColumn = "productId", entityColumn = "productId")
    val cartItemDTO: CartItemDTO?
)
