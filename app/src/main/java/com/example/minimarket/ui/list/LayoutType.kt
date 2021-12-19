package com.example.minimarket.ui.list

import androidx.viewbinding.ViewBinding
import com.example.minimarket.R
import com.example.minimarket.database.Product
import com.example.minimarket.ui.list.item.ListItem
import com.example.minimarket.ui.list.item.ListItemCell
import com.example.minimarket.ui.list.item.ListItemLine

enum class LayoutType(
    val code: Int,
    val spanCount: Int,
    val icon: Int,
    val itemFunction: (Product) -> ListItem<out ViewBinding>
) {
    GRID(0, 2, R.drawable.ic_baseline_grid_layout_24, ::ListItemCell),
    LINER(1, 0, R.drawable.ic_baseline_linear_layout_24, ::ListItemLine);

    companion object {
        private val values = values()
        val default = GRID
        fun getByCode(code: Int) = values.first { it.code == code }
    }
}