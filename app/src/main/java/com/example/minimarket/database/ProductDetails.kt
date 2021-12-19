package com.example.minimarket.database

import androidx.room.Embedded
import androidx.room.Relation

data class ProductDetails(
    @Embedded
    val product: Product,
    @Relation(parentColumn = "productId", entityColumn = "productId")
    val cartItem: CartItem?
)
