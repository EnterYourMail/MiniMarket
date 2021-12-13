package com.example.minimarket.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CartViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val cart = repository.basket.asLiveData()
    val cartCount = repository.cartCount.asLiveData()

    fun pay() = viewModelScope.launch { repository.emptyBasket() }
}