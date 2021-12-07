package com.example.minimarket.repository

import com.example.minimarket.database.CartItem
import com.example.minimarket.database.CartItemDao
import com.example.minimarket.database.Product
import com.example.minimarket.database.ProductDao
import kotlinx.coroutines.flow.Flow

class Repository(
    private val productDao: ProductDao,
    private val cartItemDao: CartItemDao
) {

    val products: Flow<List<Product>>
        get() = productDao.getAll()

    //fun getProduct(productId: Int) = productDao.getById(productId)

    fun findProducts(name: String) = productDao.findByName(name)

    suspend fun setProductQuantity(productId: Int, quantity: Int) {
        val basketItem = CartItem(productId, quantity)
        if (quantity > 0) {
            cartItemDao.replaceItem(basketItem)
        } else {
            cartItemDao.deleteItem(basketItem)
        }
    }

    fun getProductDetails(productId: Int) = productDao.getProductDetails(productId)

    fun getBasket() = productDao.getBasket()

    suspend fun emptyBasket() = cartItemDao.deleteAll()


}