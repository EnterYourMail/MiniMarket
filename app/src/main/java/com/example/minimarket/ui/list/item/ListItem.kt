package com.example.minimarket.ui.list.item

import android.view.View
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import com.example.minimarket.database.ProductDTO
import com.example.minimarket.utils.Metric
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import com.xwray.groupie.viewbinding.GroupieViewHolder

abstract class ListItem<T:ViewBinding>(
    val productDTO: ProductDTO, private val picasso: Picasso, private val metric: Metric
    ): BindableItem<T>() {

    val rootView
        get() = _rootView!!
    private var _rootView: View? = null

    override fun bind(viewHolder: GroupieViewHolder<T>, position: Int, payloads: MutableList<Any>) {
        super.bind(viewHolder, position, payloads)
        _rootView = viewHolder.root
    }

    override fun unbind(viewHolder: GroupieViewHolder<T>) {
        super.unbind(viewHolder)
        _rootView = null
    }

    protected fun String.picassoLoadInto(view: ImageView) {
        val height = (metric.height * 0.3).toInt()
        val width = (metric.width * 0.3).toInt()
        picasso.load(this).resize(width, height).centerInside().into(view)
    }

    companion object {
        const val transitionName = "list_item_transition_name"
    }
}