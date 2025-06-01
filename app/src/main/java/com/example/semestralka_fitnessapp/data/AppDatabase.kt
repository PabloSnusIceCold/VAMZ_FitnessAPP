package com.example.semestralka_fitnessapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Cvik::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cvikDao(): CvikDao
}