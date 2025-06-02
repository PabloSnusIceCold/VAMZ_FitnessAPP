package com.example.semestralka_fitnessapp.repository

import com.example.semestralka_fitnessapp.data.CustomWorkout
import com.example.semestralka_fitnessapp.data.CustomWorkoutDao
import kotlinx.coroutines.flow.Flow

class CustomWorkoutRepository(private val dao: CustomWorkoutDao) {

    fun getAll(): Flow<List<CustomWorkout>> = dao.getAll()

    suspend fun insert(workout: CustomWorkout) {
        dao.insert(workout)
    }

}
