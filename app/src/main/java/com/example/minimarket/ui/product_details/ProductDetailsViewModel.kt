package com.example.minimarket.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val repository: Repository,
    private val productId: Int
) : ViewModel() {

    val quantity = repository.getProductQuantity(productId).asLiveData()

    fun setQuantity(quantity: Int) = viewModelScope.launch {
        val value = when(quantity) {
            in 0..99 -> quantity
            in 100..Int.MAX_VALUE -> 100
            else -> 0
        }
        repository.setProductQuantity(productId, value)
    }

    fun plus() {
        quantity.value?.let {
            if (it < 100) setQuantity(it + 1)
        }
    }

    fun minus() {
        quantity.value?.let {
            if (it > 0) setQuantity(it - 1)
        }
    }

}