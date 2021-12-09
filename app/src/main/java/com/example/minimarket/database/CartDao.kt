package com.example.minimarket.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    /*@Query("SELECT * FROM basket")
    fun getAll(): Flow<List<BasketItem>>*/

    @Query("SELECT COUNT(*) FROM basket")
    fun getCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun replaceItem(item: CartItem)

    @Delete
    fun deleteItem(item: CartItem)

    @Query("DELETE FROM basket")
    fun deleteAll()
}