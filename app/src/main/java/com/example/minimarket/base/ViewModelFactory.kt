package com.example.minimarket.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.minimarket.repository.Repository
import com.example.minimarket.ui.cart.CartViewModel
import com.example.minimarket.ui.list.ListViewModel
import com.example.minimarket.ui.product_details.ProductDetailsViewModel
import com.example.minimarket.ui.product_details.ProductDetailsViewState

class ViewModelFactory(
    private val repository: Repository,
    private val productId: Int? = null
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when (modelClass) {
            ProductDetailsViewState::class.java ->
                return ProductDetailsViewState(repository, productId!!) as T
            ProductDetailsViewModel::class.java ->
                return ProductDetailsViewModel(repository, productId!!) as T
            ListViewModel::class.java ->
                return ListViewModel(repository) as T
            CartViewModel::class.java ->
                return CartViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")

    }
}