package com.example.minimarket.ui.list

import androidx.lifecycle.*
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: Repository) : ViewModel() {
    val cartCount = repository.cartCount.asLiveData()
    val products = repository.products.asLiveData()

    fun deleteAllProducts() = viewModelScope.launch { repository.deleteAll() }

    fun prepopulate() = viewModelScope.launch { repository.prepopulateProduct() }



}