package com.example.minimarket.ui.list.item

import android.view.View
import com.example.minimarket.R
import com.example.minimarket.database.ProductDTO
import com.example.minimarket.databinding.ItemCellBinding
import com.example.minimarket.utils.Metric
import com.squareup.picasso.Picasso

class ListItemCell(productDTO: ProductDTO, picasso: Picasso, metric: Metric) :
    ListItem<ItemCellBinding>(productDTO, picasso, metric) {

    override fun getLayout() = R.layout.item_cell

    override fun bind(binding: ItemCellBinding, position: Int) {
        binding.itemCellProductImage.picassoLoad(productDTO.imageLink)
        //binding.itemCellProductImage.setImageResource(productDTO.imageResource)
        binding.itemCellProductTitle.text = productDTO.name
        binding.itemCellProducerTitle.text = productDTO.producer
        binding.itemCellPriceText.text = productDTO.price.toString()
        //binding.itemCellProductImage.transitionName = transitionName + productDTO.productId
        binding.root.transitionName = transitionName + productDTO.productId
    }

    override fun initializeViewBinding(view: View): ItemCellBinding {
        return ItemCellBinding.bind(view)
    }


}