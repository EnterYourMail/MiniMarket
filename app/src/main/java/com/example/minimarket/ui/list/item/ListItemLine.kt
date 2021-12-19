package com.example.minimarket.ui.list.item

import android.view.View
import com.example.minimarket.R
import com.example.minimarket.database.Product
import com.example.minimarket.databinding.ItemLineBinding

class ListItemLine(product: Product): ListItem<ItemLineBinding>(product) {
    override fun getLayout() = R.layout.item_line

    override fun bind(binding: ItemLineBinding, position: Int) {
        binding.itemLineProductImage.setImageResource(product.imageResource)
        binding.itemLineProductTitle.text = product.name
        binding.itemLineProducerTitle.text = product.producer
        binding.itemLinePriceText.text = product.price.toString()
    }

    override fun initializeViewBinding(view: View): ItemLineBinding {
        return ItemLineBinding.bind(view)
    }

}