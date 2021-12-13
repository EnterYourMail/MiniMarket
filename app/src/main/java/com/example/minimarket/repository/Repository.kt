package com.example.minimarket.repository

import com.example.minimarket.R
import com.example.minimarket.database.CartDao
import com.example.minimarket.database.CartItem
import com.example.minimarket.database.Product
import com.example.minimarket.database.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {

    val products = productDao.getAll()
    val cartCount = cartDao.getCount().map { it ?: 0 }
    val basket = cartDao.getBasket()

    fun getProduct(productId: Int) = productDao.getById(productId)

    //fun getProductDetails(productId: Int) = productDao.getProductDetails(productId)

    fun getProductQuantity(productId: Int): Flow<Int> {
        return cartDao.getItemQuantity(productId).map { it ?: 0 }
    }

    //suspend fun getProductQuantityNow(productId: Int) =
    //cartDao.getItemQuantityNow(productId) ?: 0

    suspend fun setProductQuantity(productId: Int, quantity: Int) {
        val basketItem = CartItem(productId, quantity)
        if (quantity > 0) {
            cartDao.replaceItem(basketItem)
        } else {
            cartDao.deleteItem(basketItem)
        }
    }

    fun findProducts(name: String) = productDao.findByName(name)

    suspend fun emptyBasket() = cartDao.deleteAll()

    // For test
    suspend fun deleteAllProducts() = productDao.deleteAll()

    suspend fun prepopulateProduct() {
        val products = Array(20) { i ->
            Product(
                productId = i,
                name = "Шоколад \"Аленка\" #$i",
                producer = "Фабрика \"Красный октябрь\"",
                price = 100,
                protein = 10,
                fat = 30,
                carbohydrates = 28,
                calories = 580,
                imageResource = R.drawable.ic_launcher_background
            )
        }
        productDao.insertAll(*products)
    }
}