package com.example.semestralka_fitnessapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CustomWorkoutDao {
    @Query("SELECT * FROM custom_workouts")
    fun getAll(): Flow<List<CustomWorkout>>

    @Insert
    suspend fun insert(workout: CustomWorkout)
}

