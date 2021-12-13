package com.example.minimarket.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    //val products = repository.products.asLiveData()
    private val _searchFlow = MutableStateFlow("")
    private val searchFlow
        get() = _searchFlow.debounce(500L).distinctUntilChanged()

    val cartCount = repository.cartCount.asLiveData()
    val products = searchFlow.flatMapLatest {
        if (it.isBlank()) {
            repository.products
        } else {
            repository.findProducts(it)
        }
    }.asLiveData()

    fun findProducts(name: String) {
        _searchFlow.value = name
    }

    fun deleteAllProducts() = viewModelScope.launch { repository.deleteAllProducts() }

    fun prepopulate() = viewModelScope.launch { repository.prepopulateProduct() }

}
