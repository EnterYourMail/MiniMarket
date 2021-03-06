package com.example.minimarket.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.minimarket.R
import com.example.minimarket.repository.Repository
import com.example.minimarket.utils.Metric
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

class ListViewModel @Inject constructor(
    private val repository: Repository,
    private val picasso: Picasso) : ViewModel() {

    var metric = Metric(500, 500)
    val viewState: LiveData<ListViewState>
        get() = _viewState
    private val _viewState = MutableLiveData<ListViewState>()
    private val mutex = Mutex()

    private val _searchFlow = MutableStateFlow("")
    private val productsDTOFlow = _searchFlow.debounce(500L).flatMapLatest {
        if (it.isBlank()) {
            repository.productsDTO
        } else {
            repository.findProducts(it)
        }
    }
    private val cartCountFlow = repository.cartCount

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
        viewModelScope.launch {
            //Change layout type.
            val layoutType = when (repository.getLayoutType()) {
                LayoutType.GRID -> LayoutType.LINER
                else -> LayoutType.default
            }
            //Store layout type.
            repository.setLayoutType(layoutType)

            mutex.withLock {
                viewState.value?.let { viewStateValue ->
                    // Constructor accords layout type.
                    val itemConstructor = layoutType.itemConstructor
                    val newItems = viewStateValue.items.map { item ->
                        itemConstructor.invoke(item.productDTO, picasso, metric)
                    }
                    //Set new value.
                    _viewState.value = viewStateValue.copy(
                        layoutType = layoutType,
                        items = newItems
                    )
                }
            }

        }
        return true
    }

    private fun isCartCounterVisible(cartCount: Int) = cartCount > 0

    private fun initViewState() {
        _viewState.value = ListViewState(repository.getLayoutType())

        cartCountFlow.onEach { viewStateValue ->
            mutex.withLock {
                _viewState.value = viewState.value?.copy(
                    cartCount = viewStateValue,
                    isCartCounterVisible = isCartCounterVisible(viewStateValue)
                )
            }
        }.launchIn(viewModelScope)

        productsDTOFlow.onEach { productsDTO ->
            mutex.withLock {
                viewState.value?.let { listViewState ->
                    val itemConstructor = listViewState.layoutType.itemConstructor

                    _viewState.value = listViewState.copy(
                        items = productsDTO.map { itemConstructor(it, picasso, metric) }
                    )
                }
            }
        }.launchIn(viewModelScope)
    }



}