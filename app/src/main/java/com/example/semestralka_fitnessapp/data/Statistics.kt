package com.example.semestralka_fitnessapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statistics")
data class Statistics(
    @PrimaryKey val id: Int = 0,
    val totalWorkoutTime: Int = 0,
    val totalCaloriesBurned: Int = 0,
    val totalExercisesDone: Int = 0
)
