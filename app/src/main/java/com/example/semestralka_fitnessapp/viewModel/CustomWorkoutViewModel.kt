package com.example.semestralka_fitnessapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.semestralka_fitnessapp.data.Cvik
import com.example.semestralka_fitnessapp.repository.CvikRepository
import kotlinx.coroutines.flow.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.semestralka_fitnessapp.data.CustomWorkout
import com.example.semestralka_fitnessapp.repository.CustomWorkoutRepository
import kotlinx.coroutines.launch

data class CustomWorkoutExercise(
    val cvik: Cvik,
    val repetitionsOrDuration: Int
)

class CustomWorkoutViewModel(
    private val repository: CvikRepository,
    private val customWorkoutRepository: CustomWorkoutRepository
) : ViewModel() {


    val workoutList = customWorkoutRepository.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val allCustomWorkouts: StateFlow<List<CustomWorkout>> = customWorkoutRepository.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories: StateFlow<List<String>> = repository.allCviky
        .map { cviky -> cviky.map { it.kategoria }.distinct() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    val filteredCviky: StateFlow<List<Cvik>> = combine(repository.allCviky, selectedCategory) { cviky, category ->
        if (category == null) emptyList()
        else cviky.filter { it.kategoria == category }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedCvik = MutableStateFlow<Cvik?>(null)
    val selectedCvik: StateFlow<Cvik?> = _selectedCvik.asStateFlow()

    var repetitionsOrDuration by mutableStateOf("")

    private val _workoutName = MutableStateFlow("")
    val workoutName: StateFlow<String> = _workoutName.asStateFlow()

    private val _customWorkoutList = MutableStateFlow<List<CustomWorkoutExercise>>(emptyList())
    val customWorkoutList: StateFlow<List<CustomWorkoutExercise>> = _customWorkoutList.asStateFlow()

    fun selectCategory(category: String) {
        _selectedCategory.value = category
        _selectedCvik.value = null
    }

    fun selectCvik(cvik: Cvik) {
        _selectedCvik.value = cvik
    }

    fun addCvikToWorkout() {
        val cvik = selectedCvik.value ?: return
        val count = repetitionsOrDuration.toIntOrNull() ?: return

        val newList = _customWorkoutList.value.toMutableList()
        newList.add(CustomWorkoutExercise(cvik, count))
        _customWorkoutList.value = newList

        repetitionsOrDuration = ""
        _selectedCvik.value = null
    }

    fun saveWorkout(name: String) {
        val workout = CustomWorkout(
            name = name,
            exerciseIds = customWorkoutList.value.map { it.cvik.id },
            repsOrDurations = customWorkoutList.value.map { it.repetitionsOrDuration }
        )
        viewModelScope.launch {
            customWorkoutRepository.insert(workout)
        }
    }

    fun updateWorkoutName(name: String) {
        _workoutName.value = name
    }

    fun clearWorkoutName() {
        _workoutName.value = ""
    }

}
