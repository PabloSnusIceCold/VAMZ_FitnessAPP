package com.example.semestralka_fitnessapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka_fitnessapp.data.Cvik
import com.example.semestralka_fitnessapp.repository.StatisticsRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.*
import com.example.semestralka_fitnessapp.repository.CustomWorkoutRepository
import com.example.semestralka_fitnessapp.repository.CvikRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine

class CvikViewModel(
    private val repositoryClassic: CvikRepository,
    private val repositoryCustom: CustomWorkoutRepository,
    private val statisticsRepository: StatisticsRepository,
    private val jeKlasicky: Boolean,
    private val jeVyzva: Boolean = false,
    private val customWorkoutId: Int? = null
) : ViewModel() {

    private val _cviky = mutableStateOf<List<Cvik>>(emptyList())
    val cviky: State<List<Cvik>> = _cviky

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

    init {
        viewModelScope.launch {
            if (jeVyzva) {
                repositoryClassic.allCviky.collect { list ->
                    _cviky.value = list.filter { it.kategoria == "Extrémne" }.shuffled().take(10)
                }
            } else if (jeKlasicky) {
                repositoryClassic.allCviky.collect { list ->
                    _cviky.value = list.filter { it.kategoria != "Extrémne" }.shuffled().take(10)
                }
            } else {
                combine(
                    repositoryCustom.getAll(),
                    repositoryClassic.allCviky
                ) { workouts, allCviky ->
                    val customWorkout = workouts.find { it.id == customWorkoutId }
                    if (customWorkout != null) {
                        val customCviky = customWorkout.exerciseIds.mapIndexedNotNull { idx, cvikId ->
                            allCviky.find { it.id == cvikId }?.copy(
                                trvanieAleboOpakovania = customWorkout.repsOrDurations.getOrNull(idx)
                                    ?: 0
                            )
                        }
                        customCviky
                    } else emptyList()
                }.collect { list ->
                    _cviky.value = list
                }
            }
        }
    }

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
            totalExercisesDone++

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
            updateStatistics()
        }
    }

    fun endWorkout() {
        timerJob?.cancel()
        _workoutFinished.value = true
        workoutStarted = false

        updateStatistics()
    }
}
