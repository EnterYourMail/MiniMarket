package com.example.minimarket.repository

import com.example.minimarket.database.CartDao
import com.example.minimarket.database.CartItemDTO
import com.example.minimarket.database.ProductDao
import com.example.minimarket.ui.list.LayoutType
import com.example.minimarket.utils.SharedPreferencesHelper
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
    private val sPref: SharedPreferencesHelper
) {

    val productsDTO = productDao.getAll()
    val cartCount = cartDao.getCount().map { it ?: 0 }
    val cart = cartDao.getCart()

    fun findProducts(name: String) = productDao.findByName(name)

    fun getLayoutType() = LayoutType.getByCode(
        sPref.getInt(SharedPreferencesHelper.LAYOUT_TYPE, LayoutType.GRID.code)
    )

    fun setLayoutType(layoutType: LayoutType) = sPref.putInt(
        SharedPreferencesHelper.LAYOUT_TYPE, layoutType.code
    )

    // For test
    suspend fun deleteAllProducts() = productDao.deleteAll()

    suspend fun prepopulateProduct() = productDao.prepopulate()

    fun getProductDetails(productId: Int) = cartDao.getProductDetails(productId)

    suspend fun setProductQuantity(productId: Int, quantity: Int) {
        val basketItem = CartItemDTO(productId, quantity)
        if (quantity > 0) {
            cartDao.replaceItem(basketItem)
        } else {
            cartDao.deleteItem(basketItem)
        }
    }

    suspend fun emptyCart() = cartDao.deleteAll()
}