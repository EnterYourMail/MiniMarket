package com.example.minimarket.ui.list.item

import android.view.View
import com.example.minimarket.R
import com.example.minimarket.database.ProductDTO
import com.example.minimarket.databinding.ItemLineBinding
import com.squareup.picasso.Picasso

class ListItemLine (productDTO: ProductDTO, picasso: Picasso): ListItem<ItemLineBinding>(productDTO, picasso) {

    override lateinit var transitionView: View

    override fun getLayout() = R.layout.item_line

    override fun bind(binding: ItemLineBinding, position: Int) {
        productDTO.imageLink.picassoLoadInto(binding.itemLineProductImage)
        //binding.itemLineProductImage.setImageResource(productDTO.imageResource)
        binding.itemLineProductTitle.text = productDTO.name
        binding.itemLineProducerTitle.text = productDTO.producer
        binding.itemLinePriceText.text = productDTO.price.toString()
        //binding.itemLineProductImage.transitionName = transitionName + productDTO.productId
        binding.root.transitionName = transitionName + productDTO.productId
        transitionView = binding.root
    }

    override fun initializeViewBinding(view: View): ItemLineBinding {
        return ItemLineBinding.bind(view)
    }

}