package com.example.minimarket.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BasketItemDao {
    /*@Query("SELECT * FROM basket")
    fun getAll(): Flow<List<BasketItem>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun replaceItem(item: BasketItem)

    @Delete
    suspend fun deleteItem(item: BasketItem)

    @Delete
    suspend fun deleteAll()
}