package com.example.minimarket.ui.cart

import com.example.minimarket.database.CartDetails

data class CartViewState(
    val isPayButtonEnable: Boolean,
    val listCartDetails:List<CartDetails>,
    val cartTotal: Int
)
