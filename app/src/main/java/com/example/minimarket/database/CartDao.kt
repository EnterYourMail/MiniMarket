package com.example.minimarket.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    /*@Query("SELECT * FROM basket")
    fun getAll(): Flow<List<BasketItem>>*/

    @Query("SELECT quantity FROM basket WHERE productId = :productId")
    fun getItemQuantity(productId: Int): Flow<Int?>

    @Query("SELECT quantity FROM basket WHERE productId = :productId")
    suspend fun getItemQuantityNow(productId: Int): Int?

    @Query("SELECT SUM(quantity) FROM basket")
    fun getCount(): Flow<Int?>

    @Query("SELECT SUM(quantity) FROM basket")
    suspend fun getCountOnce(): Int?

    @Transaction
    @Query("SELECT * FROM basket")
    fun getCart(): Flow<List<CartDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replaceItem(item: CartItem)

    @Delete
    suspend fun deleteItem(item: CartItem)

    @Query("DELETE FROM basket")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM products WHERE productId = :productId")
    fun getProductDetails(productId: Int): Flow<ProductDetails>
}