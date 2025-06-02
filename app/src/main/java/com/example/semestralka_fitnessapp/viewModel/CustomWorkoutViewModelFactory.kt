package com.example.semestralka_fitnessapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.semestralka_fitnessapp.repository.CvikRepository

class CustomWorkoutViewModelFactory(
    private val repository: CvikRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomWorkoutViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomWorkoutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
