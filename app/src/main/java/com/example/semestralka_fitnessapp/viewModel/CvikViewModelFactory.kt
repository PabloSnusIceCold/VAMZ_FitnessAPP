package com.example.semestralka_fitnessapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralka_fitnessapp.repository.CvikRepository
import com.example.semestralka_fitnessapp.repository.StatisticsRepository

class CvikViewModelFactory(
    private val cvikRepository: CvikRepository,
    private val statisticsRepository: StatisticsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CvikViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CvikViewModel(cvikRepository, statisticsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}