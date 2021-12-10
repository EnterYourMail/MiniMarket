package com.example.minimarket.ui.list

import androidx.lifecycle.*
import com.example.minimarket.database.Product
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.launch
import java.lang.IllegalStateException

class ListViewModel(private val repository: Repository) : ViewModel() {
    val basketCount: LiveData<Int> = repository.cartCount.asLiveData()
    val products: LiveData<List<Product>>  = repository.products.asLiveData()

    fun deleteAllProducts() = viewModelScope.launch { repository.deleteAll() }

    fun prepopulate() = viewModelScope.launch { repository.prepopulateProduct() }

    class ListViewModelFactory(private val repository: Repository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass == ListViewModel::class.java) {
                return ListViewModel(repository) as T
            }
            throw IllegalStateException("It's only for MainActivityViewModel")
        }
    }

}