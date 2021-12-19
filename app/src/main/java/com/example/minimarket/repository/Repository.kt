package com.example.minimarket.repository

import com.example.minimarket.R
import com.example.minimarket.database.CartDao
import com.example.minimarket.database.CartItem
import com.example.minimarket.database.Product
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

    val products = productDao.getAll()
    val cartCount = cartDao.getCount().map { it ?: 0 }
    val cart = cartDao.getCart()

    //suspend fun getCartCount() = cartDao.getCountOnce() ?: 0

    //suspend fun getProducts() = productDao.getAllOnce()

    fun getProductDetails(productId: Int) = cartDao.getProductDetails(productId)

//    fun getProduct(productId: Int) = productDao.getById(productId)
//
//    fun getProductQuantity(productId: Int): Flow<Int> {
//        return cartDao.getItemQuantity(productId).map { it ?: 0 }
//    }

    suspend fun setProductQuantity(productId: Int, quantity: Int) {
        val basketItem = CartItem(productId, quantity)
        if (quantity > 0) {
            cartDao.replaceItem(basketItem)
        } else {
            cartDao.deleteItem(basketItem)
        }
    }

    fun findProducts(name: String) = productDao.findByName(name)

    suspend fun emptyCart() = cartDao.deleteAll()

    // Shared preferences
    fun getLayoutType() = LayoutType.getByCode(
        sPref.getInt(SharedPreferencesHelper.LAYOUT_TYPE, LayoutType.GRID.code)
    )

    fun setLayoutType(layoutType: LayoutType) = sPref.putInt(
        SharedPreferencesHelper.LAYOUT_TYPE, layoutType.code
    )

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