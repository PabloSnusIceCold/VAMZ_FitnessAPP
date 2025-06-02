package com.example.semestralka_fitnessapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semestralka_fitnessapp.viewModel.CustomWorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSelectionScreen(
    navController: NavController,
    customWorkoutViewModel: CustomWorkoutViewModel
) {
    val workoutList by customWorkoutViewModel.workoutList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Vyber si tréning") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(16.dp)) {
            workoutList.forEach { workout ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            navController.navigate("playCustomWorkout/${workout.id}")
                        }
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Tréning: ${workout.name}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Počet cvikov: ${workout.exerciseIds.size}")
                    }
                }
            }
        }
    }
}
