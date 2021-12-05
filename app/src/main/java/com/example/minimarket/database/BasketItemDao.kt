package com.example.minimarket.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketItemDao {
    /*@Query("SELECT * FROM basket")
    fun getAll(): Flow<List<BasketItem>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setQuantity(productId: Product, quantity: Int)

    @Delete
    fun deleteItem(productId: Product)

    @Delete
    fun deleteAll()
}