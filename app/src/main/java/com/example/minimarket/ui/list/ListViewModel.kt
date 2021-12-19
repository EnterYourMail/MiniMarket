package com.example.minimarket.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimarket.R
import com.example.minimarket.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _searchFlow = MutableStateFlow("")

    private val productsFlow = _searchFlow.flatMapLatest {
        if (it.isBlank()) {
            repository.products
        } else {
            repository.findProducts(it)
        }
    }

    private val cartCountFlow = repository.cartCount

    private val _viewState = MutableLiveData<ListViewState>()
    val viewState: LiveData<ListViewState>
        get() = _viewState

    init {
        initViewState()
    }

    fun findProducts(name: String) {
        _searchFlow.value = name
    }

    fun menuItemIdClick(id: Int) = when (id) {
        R.id.menu_layout -> changeLayoutType()
        R.id.menu_delete_all -> deleteAllProducts()
        R.id.menu_prepopulate -> prepopulate()
        else -> false
    }

    private fun deleteAllProducts(): Boolean {
        viewModelScope.launch { repository.deleteAllProducts() }
        return true
    }

    private fun prepopulate(): Boolean {
        viewModelScope.launch { repository.prepopulateProduct() }
        return true
    }

    private fun changeLayoutType(): Boolean {
        val layoutType = when (repository.getLayoutType()) {
            LayoutType.GRID -> LayoutType.LINER
            else -> LayoutType.default
        }
        repository.setLayoutType(layoutType)
        _viewState.value?.let {
            val itemFunc = layoutType.itemFunction
            val items = it.items.map { item -> itemFunc(item.product) }
            _viewState.value = it.copy(layoutType = layoutType, items = items)
        }
        return true
    }

    private fun isCartCounterVisible(cartCount: Int) = cartCount > 0

    private fun initViewState() {

//        val cartCount = repository.getCartCount()
//        val layoutType = repository.getLayoutType()
//        val itemFunction = layoutType.itemFunction
//        _viewState.value = ListViewState(
//            items = repository.getProducts().map { itemFunction(it) },
//            layoutType = layoutType,
//            cartCount = cartCount,
//            isCartCounterVisible = isCartCounterVisible(cartCount)
//        )

        _viewState.value = ListViewState(
            listOf(),
            LayoutType.default,
            0,
            false
        )

        cartCountFlow.onEach {
            _viewState.value = _viewState.value?.copy(
                cartCount = it,
                isCartCounterVisible = isCartCounterVisible(it)
            )
        }.launchIn(viewModelScope)

        productsFlow.onEach { products ->
            _viewState.value?.let { listViewState ->
                val itemFunc = listViewState.layoutType.itemFunction

                _viewState.value = listViewState.copy(
                    items = products.map { itemFunc(it) }
                )
            }
        }.launchIn(viewModelScope)
    }


}