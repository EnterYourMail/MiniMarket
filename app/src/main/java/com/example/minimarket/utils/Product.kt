package com.example.minimarket.utils

import android.graphics.Bitmap

data class Product(
    val productId: Int,
    val name: String,
    val producer: String,
    val price: Int,
    val protein: Int,
    val fat: Int,
    val carbohydrates: Int,
    val calories: Int,
    val imageResource: Int,
    val image: Bitmap
)
