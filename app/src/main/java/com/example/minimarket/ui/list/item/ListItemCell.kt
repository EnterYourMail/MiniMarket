package com.example.minimarket.ui.list.item

import android.view.View
import com.example.minimarket.R
import com.example.minimarket.database.Product
import com.example.minimarket.databinding.ItemCellBinding
import com.xwray.groupie.viewbinding.BindableItem

class ListItemCell(private val product: Product) : BindableItem<ItemCellBinding>() {
    override fun getLayout() = R.layout.item_cell

    override fun bind(binding: ItemCellBinding, position: Int) {
        binding.ivProductImage.setImageResource(product.imageResource)
        binding.tvProduct.text = product.name
        binding.tvProducer.text = product.producer
        binding.tvPrice.text = product.price.toString()
    }

    override fun initializeViewBinding(view: View): ItemCellBinding {
        return ItemCellBinding.bind(view)
    }
}