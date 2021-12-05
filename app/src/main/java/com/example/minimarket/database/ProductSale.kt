package com.example.minimarket.database

import androidx.room.Embedded
import androidx.room.Relation

data class ProductSale(
    @Embedded
    val product: Product,
    @Relation(parentColumn = "productId", entityColumn = "productId")
    val basketItem: BasketItem
)
