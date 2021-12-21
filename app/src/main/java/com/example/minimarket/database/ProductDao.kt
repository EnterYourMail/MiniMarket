package com.example.minimarket.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.minimarket.R
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAll(): Flow<List<ProductDTO>>

    @Query("SELECT * FROM products")
    suspend fun getAllOnce(): List<ProductDTO>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :text || '%'")
    fun findByName(text: String): Flow<List<ProductDTO>>

    @Query("SELECT * FROM products WHERE productId = :id")
    fun getById(id: Int): Flow<ProductDTO>

    @Insert
    suspend fun insertAll(vararg productDTOs: ProductDTO)

    @Query("DELETE FROM products")
    suspend fun deleteAll()

    suspend fun prepopulate() {
        insertAll(*makeProducts())
    }

    private fun makeProducts() = Array(20) { i ->
        ProductDTO(
            productId = i,
            name = "Шоколад \"Аленка\" #$i",
            producer = "Фабрика \"Красный октябрь\"",
            price = 100,
            protein = 10,
            fat = 30,
            carbohydrates = 28,
            calories = 580,
            imageResource = R.drawable.ic_launcher_background,
            imageLink = "https://phonoteka.org/uploads/posts/2021-07/1625674705_32-phonoteka-org-p-shokolad-art-krasivo-33.jpg"
        )
    }
}