package com.example.semestralka_fitnessapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.semestralka_fitnessapp.R
import com.example.semestralka_fitnessapp.viewModel.StatisticsViewModel

@Composable
fun StatisticsScreen(
    navController: NavController,
    viewModel: StatisticsViewModel = viewModel()
) {
    val statistics by viewModel.statistics.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.statistics_background),
            contentDescription = "Pozadie",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Button(onClick = {
                navController.navigate("menu")
            }) {
                Text(stringResource(id = R.string.back_arrow), color = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.3f))
            Text(
                text = stringResource(id = R.string.your_statistics),
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.total_time, statistics.totalWorkoutTime),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.total_calories, statistics.totalCaloriesBurned),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.total_exercises, statistics.totalExercisesDone),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(0.7f))
        }
    }
}
