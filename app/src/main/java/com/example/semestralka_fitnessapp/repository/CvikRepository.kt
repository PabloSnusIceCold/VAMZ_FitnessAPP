package com.example.semestralka_fitnessapp.repository

import com.example.semestralka_fitnessapp.data.Cvik
import com.example.semestralka_fitnessapp.data.CvikDao
import kotlinx.coroutines.flow.Flow

class CvikRepository(private val dao: CvikDao) {

    val allCviky: Flow<List<Cvik>> = dao.getAllCviky()

    suspend fun insert(cvik: Cvik) {
        dao.insertCvik(cvik)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}