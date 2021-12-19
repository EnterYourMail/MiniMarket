package com.example.minimarket.di

import android.content.Context
import com.example.minimarket.ui.cart.CartFragment
import com.example.minimarket.ui.list.ListFragment
import com.example.minimarket.ui.product_details.ProductDetailsFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
@Component(modules = [StorageModule::class])
interface AppComponent {

    //fun repository(): Repository
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance scope: CoroutineScope,
        ): AppComponent
    }

    fun inject(fragment: ListFragment)
    fun inject(fragment: ProductDetailsFragment)
    fun inject(fragment: CartFragment)

}