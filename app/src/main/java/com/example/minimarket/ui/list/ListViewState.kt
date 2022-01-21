package com.example.minimarket.ui.list

import androidx.viewbinding.ViewBinding
import com.example.minimarket.ui.list.item.ListItem

data class ListViewState constructor(
    val items: List<ListItem<out ViewBinding>>,
    val layoutType: LayoutType,
    val cartCount: Int,
    val isCartCounterVisible: Boolean
) {

    constructor(layoutType: LayoutType): this(
        items = listOf(),
        layoutType = layoutType,
        cartCount = 0,
        isCartCounterVisible = false
    )

}
