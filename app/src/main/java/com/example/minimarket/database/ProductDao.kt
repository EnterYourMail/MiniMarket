package com.example.minimarket.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM PRODUCTS")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM PRODUCTS WHERE NAME LIKE :text")
    fun findByName(text: String): Flow<List<Product>>

    @Insert
    fun insertAll(vararg products: Product)

    @Delete
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM products WHERE productId = :productId")
    fun getProductSale(productId: Int): Flow<ProductSale>

    @Transaction
    @Query("SELECT * FROM products")
    fun getBasket(): Flow<List<ProductSale>>
}