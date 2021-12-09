package com.example.minimarket.repository

import com.example.minimarket.database.CartItem
import com.example.minimarket.database.CartDao
import com.example.minimarket.database.Product
import com.example.minimarket.database.ProductDao
import kotlinx.coroutines.flow.Flow

class Repository(
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {

    val products: Flow<List<Product>>
        get() = productDao.getAll()
    val cartCount: Flow<Int>
        get() = cartDao.getCount()

    //fun getProduct(productId: Int) = productDao.getById(productId)

    fun findProducts(name: String) = productDao.findByName(name)

    suspend fun setProductQuantity(productId: Int, quantity: Int) {
        val basketItem = CartItem(productId, quantity)
        if (quantity > 0) {
            cartDao.replaceItem(basketItem)
        } else {
            cartDao.deleteItem(basketItem)
        }
    }

    fun getProductDetails(productId: Int) = productDao.getProductDetails(productId)

    fun getBasket() = productDao.getBasket()

    suspend fun emptyBasket() = cartDao.deleteAll()


}