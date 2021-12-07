package com.example.minimarket.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket")
data class CartItem(
    @PrimaryKey val productId: Int,
    val quantity: Int
)
