package com.example.minimarket.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.minimarket.R
import com.example.minimarket.database.CartDao
import com.example.minimarket.database.LocalDatabase
import com.example.minimarket.database.Product
import com.example.minimarket.database.ProductDao
import com.example.minimarket.ui.MainActivity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Module
class StorageModule {

    lateinit var localDatabase: LocalDatabase

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            MainActivity::class.java.toString(),
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideProductDao(database: LocalDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideCartDao(database: LocalDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideDatabase(context: Context, scope: CoroutineScope): LocalDatabase {
        localDatabase = Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            "local_database"
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    scope.launch {
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
                        localDatabase.productDao().insertAll(*products)
                    }
                }
            })
            .build()
        return localDatabase
    }


}