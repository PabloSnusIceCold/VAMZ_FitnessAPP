package com.example.semestralka_fitnessapp.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cviky")
data class Cvik(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nazov: String,
    val kategoria: String,
    val trvanieAleboOpakovania: Int,
    val obrazok: String
)