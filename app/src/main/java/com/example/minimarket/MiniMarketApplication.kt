package com.example.minimarket

import android.app.Application
import com.example.minimarket.database.LocalDatabase
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MiniMarketApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { LocalDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { Repository(database.productDao(), database.cartDao()) }
}