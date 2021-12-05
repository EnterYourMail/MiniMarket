package com.example.minimarket.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket")
data class BasketItem(
    @PrimaryKey val productId: Int,
    val quantity: Int
)
