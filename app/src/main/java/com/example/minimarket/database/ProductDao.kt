package com.example.minimarket.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<Product>>

    @Query("SELECT * FROM products")
    suspend fun getAllOnce(): List<Product>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :text || '%'")
    fun findByName(text: String): Flow<List<Product>>

    @Query("SELECT * FROM products WHERE productId = :id")
    fun getById(id: Int): Flow<Product>

    @Insert
    suspend fun insertAll(vararg products: Product)

    @Query("DELETE FROM products")
    suspend fun deleteAll()




}