package com.example.minimarket.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.minimarket.database.ProductDetails
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val repository: Repository,
    private val productId: Int
) : ViewModel() {

    val viewState = repository.getProductDetails(productId).map(
        ::productDetailsToViewState
    ).asLiveData()

    fun setStringQuantity(quantity: String) {
        quantity.toIntOrNull()?.let { setQuantity(it) }
    }

    fun plus() {
        viewState.value?.quantity?.let {
            if (it < 100) setQuantity(it + 1)
        }
    }

    fun minus() {
        viewState.value?.quantity?.let {
            if (it > 0) setQuantity(it - 1)
        }
    }

    private fun setQuantity(quantity: Int) = viewModelScope.launch {
        val value = when (quantity) {
            in 0..99 -> quantity
            in 100..Int.MAX_VALUE -> 100
            else -> 0
        }
        repository.setProductQuantity(productId, value)
    }

    private fun productDetailsToViewState(productDetails: ProductDetails): ProductDetailsViewState {
        return ProductDetailsViewState(
            productDetails.productDTO,
            productDetails.cartItemDTO?.quantity ?: 0
        )
    }
}