package com.example.minimarket.database

import androidx.room.*

@Dao
interface CartItemDao {
    /*@Query("SELECT * FROM basket")
    fun getAll(): Flow<List<BasketItem>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replaceItem(item: CartItem)

    @Delete
    suspend fun deleteItem(item: CartItem)

    @Delete
    suspend fun deleteAll()
}