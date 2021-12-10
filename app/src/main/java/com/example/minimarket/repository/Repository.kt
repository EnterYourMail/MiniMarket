package com.example.minimarket.repository

import com.example.minimarket.R
import com.example.minimarket.database.CartItem
import com.example.minimarket.database.CartDao
import com.example.minimarket.database.Product
import com.example.minimarket.database.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Repository(
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {

    val products: Flow<List<Product>>
        get() = productDao.getAll()
    val cartCount: Flow<Int>
        get() = cartDao.getCount()

    //fun getProduct(productId: Int) = productDao.getById(productId)

    suspend fun deleteAll() = productDao.deleteAll()

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