package com.example.minimarket.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.minimarket.database.CartDetails
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val viewState = repository.cart.mapLatest { listCartDetailsToViewState(it) }.asLiveData()

    fun pay() = viewModelScope.launch { repository.emptyCart() }

    private fun listCartDetailsToViewState(listCartDetails: List<CartDetails>): CartViewState {
        return CartViewState(
            isPayButtonEnable = listCartDetails.isNotEmpty(),
            listCartDetails = listCartDetails,
            cartTotal = listCartDetails.sumOf { it.productDTO.price * it.cartItemDTO.quantity }
        )
    }
}