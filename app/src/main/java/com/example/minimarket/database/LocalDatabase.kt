package com.example.minimarket.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.minimarket.utils.Utils

@Database(entities = [ProductDTO::class, CartItemDTO::class], version = 2, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao

    class Migration1To2 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            val defaultImageLink = Utils().getDefaultImageLink()
            database.execSQL("ALTER TABLE products ADD COLUMN imageLink TEXT NOT NULL")
            database.execSQL(
                "UPDATE products SET imageLink = ?",
                arrayOf(defaultImageLink)
            )
        }
    }

}
