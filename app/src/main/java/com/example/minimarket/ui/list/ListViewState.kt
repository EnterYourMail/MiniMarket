package com.example.minimarket.ui.list

import androidx.viewbinding.ViewBinding
import com.example.minimarket.ui.list.item.ListItem

data class ListViewState(
    val items: List<ListItem<out ViewBinding>>,
    val layoutType: LayoutType,
    val cartCount: Int,
    val isCartCounterVisible: Boolean
) {
    companion object {
        val empty = ListViewState(listOf(), LayoutType.default, 0, false)
    }
}
