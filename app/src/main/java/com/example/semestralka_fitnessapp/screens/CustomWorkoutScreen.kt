package com.example.semestralka_fitnessapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestralka_fitnessapp.viewModel.CustomWorkoutViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.semestralka_fitnessapp.R

@Composable
fun CustomWorkoutScreen(
    navController: NavController,
    viewModel: CustomWorkoutViewModel = viewModel()
) {

    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val filteredCviky by viewModel.filteredCviky.collectAsState()
    val selectedCvik by viewModel.selectedCvik.collectAsState()
    val customWorkoutList by viewModel.customWorkoutList.collectAsState()
    val allSavedWorkouts by viewModel.allCustomWorkouts.collectAsState(initial = emptyList())

    val workoutName by viewModel.workoutName.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            onClick = { navController.navigate("menu") },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .zIndex(1f)
        ) {
            Text(text = stringResource(id = R.string.back_arrow), color = Color.White)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 72.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {

                Column(modifier = Modifier.weight(1f)) {
                    Text("Kategórie", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    categories.forEach { category ->
                        val isSelected = category == selectedCategory
                        Text(
                            text = category,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(if (isSelected) Color.Gray else Color.Transparent)
                                .clickable { viewModel.selectCategory(category) },
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text("Cviky", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    filteredCviky.forEach { cvik ->
                        val isSelected = cvik == selectedCvik
                        Text(
                            text = cvik.nazov,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(if (isSelected) Color.Gray else Color.Transparent)
                                .clickable { viewModel.selectCvik(cvik) },
                            color = if (isSelected) Color.White else Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(id = R.string.add_exercise), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.repetitionsOrDuration,
                        onValueChange = { input ->
                            viewModel.repetitionsOrDuration = input.filter { it.isDigit() }
                        },
                        label = { Text(stringResource(id = R.string.repetitions_or_seconds)) },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.addCvikToWorkout() },
                        enabled = selectedCvik != null && viewModel.repetitionsOrDuration.isNotEmpty()
                    ) {
                        Text(stringResource(id = R.string.add))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(stringResource(id = R.string.custom_workout), style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    customWorkoutList.forEach {
                        Text("${it.cvik.nazov} - ${it.repetitionsOrDuration}")
                    }

                    if (customWorkoutList.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = workoutName,
                            onValueChange = { viewModel.updateWorkoutName(it) },
                            label = { Text(stringResource(id = R.string.workout_name)) },
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                viewModel.saveWorkout(workoutName)
                                viewModel.clearWorkoutName()
                            },
                            enabled = workoutName.isNotBlank()
                        ) {
                            Text(stringResource(id = R.string.save_workout))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.saved_workouts),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            allSavedWorkouts.forEach { workout ->
                Text(
                    text = stringResource(R.string.point) + " " + workout.name
                )
            }
        }
    }
}