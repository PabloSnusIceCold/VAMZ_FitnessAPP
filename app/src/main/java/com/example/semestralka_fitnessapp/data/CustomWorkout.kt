package com.example.semestralka_fitnessapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_workouts")
data class CustomWorkout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val exerciseIds: List<Int>,
    val repsOrDurations: List<Int>
)
