package com.example.minimarket.ui.list.item

import androidx.viewbinding.ViewBinding
import com.example.minimarket.database.Product
import com.xwray.groupie.viewbinding.BindableItem

abstract class ListItem<T:ViewBinding>(val product: Product): BindableItem<T>() {
    //fun getProductId() = product.productId
}