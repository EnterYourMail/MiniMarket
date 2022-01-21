package com.example.minimarket.ui.list.item

import android.view.View
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.example.minimarket.database.ProductDTO
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem

abstract class ListItem<T:ViewBinding>(
    val productDTO: ProductDTO, private val picasso: Picasso
    ): BindableItem<T>() {

    protected abstract var transitionView: View

    fun getView() = transitionView

    protected fun String.picassoLoadInto(view: ImageView) {
        picasso.load(this).resize(150, 150).centerInside().into(view)
    }

    companion object {
        const val transitionName = "list_item_transition_name"
    }
}