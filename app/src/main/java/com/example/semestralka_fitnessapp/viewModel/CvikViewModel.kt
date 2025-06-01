package com.example.semestralka_fitnessapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka_fitnessapp.data.Cvik
import com.example.semestralka_fitnessapp.repository.CvikRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CvikViewModel(private val repository: CvikRepository) : ViewModel() {

    val cviky = repository.allCviky.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun addCvik(cvik: Cvik) {
        viewModelScope.launch {
            repository.insert(cvik)
        }
    }
}

