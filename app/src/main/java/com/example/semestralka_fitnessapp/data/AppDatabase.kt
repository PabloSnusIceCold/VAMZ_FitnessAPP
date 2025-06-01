package com.example.semestralka_fitnessapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Cvik::class, Statistics::class],
    version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cvikDao(): CvikDao
    abstract fun statisticsDao(): StatisticsDao
}