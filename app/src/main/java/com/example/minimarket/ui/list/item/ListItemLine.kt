package com.example.minimarket.ui.list.item

import android.view.View
import com.example.minimarket.R
import com.example.minimarket.database.Product
import com.example.minimarket.databinding.ItemLineBinding
import com.xwray.groupie.viewbinding.BindableItem

class ListItemLine(private val product: Product) : BindableItem<ItemLineBinding>() {
    override fun getLayout() = R.layout.item_line

    override fun bind(binding: ItemLineBinding, position: Int) {
        binding.ivProductImage.setImageResource(product.imageResource)
        binding.tvProduct.text = product.name
        binding.tvProducer.text = product.producer
        binding.tvPrice.text = product.price.toString()
    }

    override fun initializeViewBinding(view: View): ItemLineBinding {
        return ItemLineBinding.bind(view)
    }
}