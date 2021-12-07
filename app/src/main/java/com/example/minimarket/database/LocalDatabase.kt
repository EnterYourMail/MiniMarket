package com.example.minimarket.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.minimarket.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Product::class, CartItem::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun basketItemDao(): CartItemDao

    private class LocalDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        suspend fun prepopulateProduct(dao: ProductDao) {
            val products = Array(20) { i ->
                Product(
                    productId = i,
                    name = "Шоколад \"Аленка\" #$i",
                    producer = "Фабрика \"Красный октябрь\"",
                    price = 100,
                    protein = 10,
                    fat = 30,
                    carbohydrates = 28,
                    calories = 580,
                    imageResource = R.drawable.ic_launcher_background
                )
            }
            dao.insertAll(*products)
        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    prepopulateProduct(database.productDao())
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): LocalDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "local_database"
                )
                    .addCallback(LocalDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
