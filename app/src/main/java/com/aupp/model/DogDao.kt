package com.aupp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {
    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FROM dogbreed")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM DOGBREED WHERE uuid = :dogId")
    suspend fun getDog(dogId: Int)

    @Query("DELETE FROM DOGBREED")
    suspend fun deleteAllDogs()
}