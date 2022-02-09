package com.example.minimarket.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.minimarket.database.CartDao
import com.example.minimarket.database.LocalDatabase
import com.example.minimarket.database.ProductDao
import com.example.minimarket.ui.MainActivity
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
class StorageModule {

    lateinit var localDatabase: LocalDatabase

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            MainActivity::class.java.toString(),
            Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun providePicasso(): Picasso {
        return Picasso.get()
    }

    @Singleton
    @Provides
    fun provideProductDao(database: LocalDatabase): ProductDao {
        return database.productDao()
    }

    @Singleton
    @Provides
    fun provideCartDao(database: LocalDatabase): CartDao {
        return database.cartDao()
    }

    @Singleton
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
                    scope.launch { localDatabase.productDao().prepopulate() }
                }
            })
            .addMigrations(LocalDatabase.Migration1To2())
            .fallbackToDestructiveMigration()
            .build()
        return localDatabase
    }


}