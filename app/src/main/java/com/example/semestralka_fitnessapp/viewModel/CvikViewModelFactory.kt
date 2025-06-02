package com.example.semestralka_fitnessapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralka_fitnessapp.repository.CustomWorkoutRepository
import com.example.semestralka_fitnessapp.repository.CvikRepository
import com.example.semestralka_fitnessapp.repository.StatisticsRepository

class CvikViewModelFactory(
    private val repositoryClassic: CvikRepository,
    private val repositoryCustom: CustomWorkoutRepository,
    private val statisticsRepository: StatisticsRepository,
    private val jeKlasicky: Boolean,
    private val customWorkoutId: Int? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CvikViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CvikViewModel(
                repositoryClassic,
                repositoryCustom,
                statisticsRepository,
                jeKlasicky,
                customWorkoutId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
