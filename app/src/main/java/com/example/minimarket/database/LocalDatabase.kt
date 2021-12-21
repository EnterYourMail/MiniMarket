package com.example.minimarket.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductDTO::class, CartItemDTO::class], version = 2, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

}
