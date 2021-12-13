package com.example.minimarket

import android.app.Application
import com.example.minimarket.di.AppComponent
import com.example.minimarket.di.DaggerAppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MiniMarketApplication : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(
            applicationContext,
            CoroutineScope(SupervisorJob())
        )
    }

    //val applicationScope = CoroutineScope(SupervisorJob())
    //val database by lazy { LocalDatabase.getDatabase(this, applicationScope) }
    //val repository by lazy { Repository(database.productDao(), database.cartDao()) }
}