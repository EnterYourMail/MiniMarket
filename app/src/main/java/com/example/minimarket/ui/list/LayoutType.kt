package com.example.minimarket.ui.list

import androidx.viewbinding.ViewBinding
import com.example.minimarket.R
import com.example.minimarket.database.ProductDTO
import com.example.minimarket.ui.list.item.ListItem
import com.example.minimarket.ui.list.item.ListItemCell
import com.example.minimarket.ui.list.item.ListItemLine
import com.example.minimarket.utils.Metric
import com.squareup.picasso.Picasso

enum class LayoutType(
    val code: Int,
    val spanCount: Int,
    val icon: Int,
    val itemConstructor: (ProductDTO, Picasso, Metric) -> ListItem<out ViewBinding>
) {
    GRID(
        code = 0,
        spanCount = 2,
        icon = R.drawable.ic_baseline_grid_layout_24,
        itemConstructor = ::ListItemCell
    ),
    LINER(
        code = 1,
        spanCount = 0,
        icon = R.drawable.ic_baseline_linear_layout_24,
        itemConstructor = ::ListItemLine
    );

    companion object {
        val default = GRID
        private val values = values()
        fun getByCode(code: Int) = values.first { it.code == code }
    }
}