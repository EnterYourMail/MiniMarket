package com.example.minimarket.database

import androidx.room.Embedded
import androidx.room.Relation

data class CartDetails(
    @Embedded
    val cartItemDTO: CartItemDTO,
    @Relation(parentColumn = "productId", entityColumn = "productId")
    val productDTO: ProductDTO
)
