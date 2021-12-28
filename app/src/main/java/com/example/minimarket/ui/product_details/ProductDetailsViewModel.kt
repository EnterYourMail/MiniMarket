package com.example.minimarket.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimarket.database.ProductDetails
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val repository: Repository,
    private val productId: Int
) : ViewModel() {

    val viewState = repository.getProductDetails(productId).map(
        ::productDetailsToViewState
    ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    fun setStringQuantity(quantity: String) {
        quantity.toIntOrNull()?.let { setQuantity(it) }
    }

    private fun setQuantity(quantity: Int) = viewModelScope.launch {
        repository.setProductQuantity(productId, quantity)
    }

    fun plus() {
        viewState.value?.quantity?.let { setQuantity(it + 1) }
    }

    fun minus() {
        viewState.value?.quantity?.let { setQuantity(it - 1) }
    }

    private fun productDetailsToViewState(productDetails: ProductDetails): ProductDetailsViewState {
        return ProductDetailsViewState(
            productDetails.productDTO,
            productDetails.cartItemDTO?.quantity ?: 0
        )
    }
}