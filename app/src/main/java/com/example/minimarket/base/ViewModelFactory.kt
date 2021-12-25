package com.example.minimarket.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minimarket.repository.Repository
import com.example.minimarket.ui.cart.CartViewModel
import com.example.minimarket.ui.list.ListViewModel
import com.example.minimarket.ui.product_details.ProductDetailsViewModel
import com.squareup.picasso.Picasso
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ViewModelFactory @AssistedInject constructor(
    private val repository: Repository,
    private val picasso: Picasso,
    @Assisted private val productId: Int?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when (modelClass) {
            ProductDetailsViewModel::class.java ->
                return ProductDetailsViewModel(repository, productId!!) as T
            ListViewModel::class.java ->
                return ListViewModel(repository, picasso) as T
            CartViewModel::class.java ->
                return CartViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }

    @AssistedFactory
    interface  Factory {
        fun create(productId: Int? = null): ViewModelFactory
    }
}
