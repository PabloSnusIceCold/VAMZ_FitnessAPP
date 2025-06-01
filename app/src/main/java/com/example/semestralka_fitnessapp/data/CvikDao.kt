package com.example.semestralka_fitnessapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CvikDao {
    @Query("SELECT * FROM cviky")
    fun getAllCviky(): Flow<List<Cvik>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCvik(cvik: Cvik)
}