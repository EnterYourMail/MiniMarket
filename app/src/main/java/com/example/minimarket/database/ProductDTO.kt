package com.example.minimarket.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductDTO(
    @PrimaryKey //(autoGenerate = true)
    val productId: Int,
    val name: String,
    val producer: String,
    val price: Int,
    val protein: Int,
    val fat: Int,
    val carbohydrates: Int,
    val calories: Int,
    val imageResource: Int,
    val imageLink: String
)
