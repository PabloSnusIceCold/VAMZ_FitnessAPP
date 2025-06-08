package com.example.semestralka_fitnessapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.semestralka_fitnessapp.R
import com.example.semestralka_fitnessapp.viewModel.CustomWorkoutViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutSelectionScreen(
    navController: NavController,
    customWorkoutViewModel: CustomWorkoutViewModel
) {
    val workoutList by customWorkoutViewModel.workoutList.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.choose_workout)) })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            Button(
                onClick = {
                    navController.navigate("menu")
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .zIndex(1f)
            ) {
                Text(
                    text = stringResource(id = R.string.back_arrow),
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .padding(
                        top = 72.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    ) // nech nezakrýva tlačidlo
                    .verticalScroll(scrollState)
            ) {
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
                            Text(text = stringResource(id = R.string.training, workout.name))
                            Text(
                                text = stringResource(
                                    id = R.string.exercise_count,
                                    workout.exerciseIds.size
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
