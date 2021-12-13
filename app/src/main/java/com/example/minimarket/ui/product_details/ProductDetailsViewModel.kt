package com.example.minimarket.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.launch

class ProductDetailsViewModel (
    private val repository: Repository,
    private val productId: Int
) : ViewModel() {

    val quantity = repository.getProductQuantity(productId).asLiveData()


    fun setQuantity(quantity: Int) = viewModelScope.launch {
        val value = if ( quantity < 100 ) quantity else 100
        repository.setProductQuantity(productId, value)
    }

    fun plus() {
        quantity.value?.let {
            if (it < 100) setQuantity(it + 1) }
    }

    fun minus() {
        quantity.value?.let {
            if (it > 0) setQuantity(it - 1)
        }
    }

}