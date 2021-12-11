package com.example.minimarket.database

import androidx.room.Embedded
import androidx.room.Relation

data class CartDetails(
    @Embedded
    val cartItem: CartItem,
    @Relation(parentColumn = "productId", entityColumn = "productId")
    val product: Product
)
