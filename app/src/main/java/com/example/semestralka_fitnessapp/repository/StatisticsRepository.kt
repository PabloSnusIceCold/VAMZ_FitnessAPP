package com.example.semestralka_fitnessapp.repository

import com.example.semestralka_fitnessapp.data.Statistics
import com.example.semestralka_fitnessapp.data.StatisticsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class StatisticsRepository(private val dao: StatisticsDao) {

    val statisticsFlow: Flow<Statistics?> = dao.getStatistics()

    suspend fun updateStatistics(
        addedTime: Int,
        addedCalories: Int,
        addedExercises: Int
    ) {
        val currentStats = statisticsFlow.first() ?: Statistics()
        val newStats = currentStats.copy(
            totalWorkoutTime = currentStats.totalWorkoutTime + addedTime,
            totalCaloriesBurned = currentStats.totalCaloriesBurned + addedCalories,
            totalExercisesDone = currentStats.totalExercisesDone + addedExercises
        )
        dao.insertStatistics(newStats)
    }
}
