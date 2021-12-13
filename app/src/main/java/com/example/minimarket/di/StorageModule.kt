package com.example.minimarket.di

import android.content.Context
import android.content.SharedPreferences
import com.example.minimarket.database.CartDao
import com.example.minimarket.database.LocalDatabase
import com.example.minimarket.database.ProductDao
import com.example.minimarket.ui.MainActivity
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope

@Module
class StorageModule {

    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            MainActivity::class.java.toString(),
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideDatabase(context: Context, scope: CoroutineScope): LocalDatabase {
        return LocalDatabase.getDatabase(context, scope)
    }

    @Provides
    fun provideProductDao(database: LocalDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    fun provideCartDao(database: LocalDatabase): CartDao {
        return database.cartDao()
    }

}