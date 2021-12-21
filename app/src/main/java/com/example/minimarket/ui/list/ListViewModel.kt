package com.example.minimarket.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimarket.R
import com.example.minimarket.repository.Repository
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val repository: Repository,
    private val picasso: Picasso) : ViewModel() {

    private val _searchFlow = MutableStateFlow("")

    private val productsDTOFlow = _searchFlow.debounce(500L).flatMapLatest {
        if (it.isBlank()) {
            repository.productsDTO
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
            val itemConstructor = layoutType.itemConstructor
            val newItems = it.items.map { item ->
                itemConstructor(item.productDTO, picasso)
            }
            _viewState.value = it.copy(layoutType = layoutType, items = newItems)
        }
        return true
    }

    private fun isCartCounterVisible(cartCount: Int) = cartCount > 0

    private fun initViewState() {
        _viewState.value = ListViewState.empty

        cartCountFlow.onEach {
            _viewState.value = _viewState.value?.copy(
                cartCount = it,
                isCartCounterVisible = isCartCounterVisible(it)
            )
        }.launchIn(viewModelScope)

        productsDTOFlow.onEach { productsDTO ->
            _viewState.value?.let { listViewState ->
                val itemConstructor = listViewState.layoutType.itemConstructor

                _viewState.value = listViewState.copy(
                    items = productsDTO.map { itemConstructor(it, picasso) }
                )
            }
        }.launchIn(viewModelScope)
    }


}