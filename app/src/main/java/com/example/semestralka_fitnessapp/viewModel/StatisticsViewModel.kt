package com.example.semestralka_fitnessapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka_fitnessapp.data.Statistics
import com.example.semestralka_fitnessapp.repository.StatisticsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map

class StatisticsViewModel(
    private val repository: StatisticsRepository
) : ViewModel() {

    val statistics = repository.statisticsFlow
        .map { it ?: Statistics() } // ak je null, vráť default štatistiky
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Statistics()
        )
}
