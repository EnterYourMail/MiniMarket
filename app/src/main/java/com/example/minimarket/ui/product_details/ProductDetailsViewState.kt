package com.example.minimarket.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.minimarket.repository.Repository

class ProductDetailsViewState(
    repository: Repository,
    productId: Int
) : ViewModel() {

    val product = repository.getProduct(productId).asLiveData()
    //fun getProduct(productId: Int) = repository.getProduct(productId).asLiveData()

}