package com.example.semestralka_fitnessapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Cvik::class, Statistics::class, CustomWorkout::class],
    version = 5
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cvikDao(): CvikDao
    abstract fun statisticsDao(): StatisticsDao
    abstract fun customWorkoutDao(): CustomWorkoutDao
}
