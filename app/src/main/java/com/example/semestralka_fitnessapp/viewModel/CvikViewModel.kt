package com.example.semestralka_fitnessapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka_fitnessapp.data.Cvik
import com.example.semestralka_fitnessapp.repository.CvikRepository
import com.example.semestralka_fitnessapp.repository.StatisticsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class CvikViewModel(
    private val repository: CvikRepository,
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    val cviky = repository.allCviky.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    var currentIndex by mutableStateOf(0)
        private set

    var remainingTime by mutableStateOf(0)
        private set

    var showPredImage by mutableStateOf(true)
        private set

    private var timerJob: Job? = null
    private var workoutStarted = false

    private var totalCalories = 0
    private var totalWorkoutTime = 0
    private var totalExercisesDone = 0

    private val _workoutFinished = mutableStateOf(false)
    val workoutFinished: State<Boolean> = _workoutFinished

    fun startWorkout() {
        if (workoutStarted) return
        workoutStarted = true

        cviky.value.getOrNull(currentIndex)?.let {
            startCvik(it)
        }
    }

    private fun startCvik(cvik: Cvik) {
        remainingTime = cvik.trvanieAleboOpakovania
        showPredImage = true

        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (remainingTime > 0) {
                delay(1000)
                remainingTime--
                showPredImage = !showPredImage
            }

            totalCalories += cvik.pocetKalorii
            totalWorkoutTime += cvik.trvanieAleboOpakovania
            totalExercisesDone += 1

            goToNextCvik()
        }
    }

    fun goToNextCvik() {
        if (currentIndex < cviky.value.lastIndex) {
            currentIndex++
            cviky.value.getOrNull(currentIndex)?.let {
                startCvik(it)
            }
        } else {
            timerJob?.cancel()
            _workoutFinished.value = true
            updateStatistics()
        }
    }

    private fun updateStatistics() {
        viewModelScope.launch {
            statisticsRepository.updateStatistics(
                addedTime = totalWorkoutTime,
                addedCalories = totalCalories,
                addedExercises = totalExercisesDone
            )
        }
    }

    fun resetWorkout() {
        timerJob?.cancel()
        workoutStarted = false
        currentIndex = 0
        totalCalories = 0
        totalWorkoutTime = 0
        totalExercisesDone = 0
        _workoutFinished.value = false

        cviky.value.getOrNull(currentIndex)?.let {
            startCvik(it)
        }
    }

    fun getTotalCalories(): Int = totalCalories

    fun skipCurrentExercise() {
        if (currentIndex < cviky.value.size - 1) {
            currentIndex++
            cviky.value.getOrNull(currentIndex)?.let {
                startCvik(it)
            }
        } else {
            _workoutFinished.value = true
        }
    }
}
