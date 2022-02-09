package com.example.minimarket.ui.list.item

import android.view.View
import com.example.minimarket.R
import com.example.minimarket.database.ProductDTO
import com.example.minimarket.databinding.ItemLineBinding
import com.example.minimarket.utils.Metric
import com.squareup.picasso.Picasso

class ListItemLine(productDTO: ProductDTO, picasso: Picasso, metric: Metric) :
    ListItem<ItemLineBinding>(productDTO, picasso, metric) {

    override fun getLayout() = R.layout.item_line

    override fun bind(binding: ItemLineBinding, position: Int) {
        binding.itemLineProductImage.picassoLoad(productDTO.imageLink)
        //binding.itemLineProductImage.setImageResource(productDTO.imageResource)
        binding.itemLineProductTitle.text = productDTO.name
        binding.itemLineProducerTitle.text = productDTO.producer
        binding.itemLinePriceText.text = productDTO.price.toString()
        //binding.itemLineProductImage.transitionName = transitionName + productDTO.productId
        binding.root.transitionName = transitionName + productDTO.productId
    }

    override fun initializeViewBinding(view: View): ItemLineBinding {
        return ItemLineBinding.bind(view)
    }

}