package com.example.minimarket.ui.list.item

import android.view.View
import com.example.minimarket.R
import com.example.minimarket.database.ProductDTO
import com.example.minimarket.databinding.ItemCellBinding
import com.squareup.picasso.Picasso

class ListItemCell (productDTO: ProductDTO, picasso: Picasso): ListItem<ItemCellBinding>(productDTO, picasso) {
    override fun getLayout() = R.layout.item_cell

    override fun bind(binding: ItemCellBinding, position: Int) {
        productDTO.imageLink.picassoLoadInto(binding.itemCellProductImage)
        //binding.itemCellProductImage.setImageResource(productDTO.imageResource)
        binding.itemCellProductTitle.text = productDTO.name
        binding.itemCellProducerTitle.text = productDTO.producer
        binding.itemCellPriceText.text = productDTO.price.toString()
    }

    override fun initializeViewBinding(view: View): ItemCellBinding {
        return ItemCellBinding.bind(view)
    }
}