package com.example.semestralka_fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.semestralka_fitnessapp.viewModel.CvikViewModel

@Composable
fun ClassicWorkoutScreen(
    navController: NavController,
    viewModel: CvikViewModel = viewModel()
) {
    val cviky by viewModel.cviky.collectAsState()

    val currentIndex = viewModel.currentIndex
    val remainingTime = viewModel.remainingTime
    val showPredImage = viewModel.showPredImage
    val workoutFinished by viewModel.workoutFinished

    val currentCvik = cviky.getOrNull(currentIndex)

    LaunchedEffect(cviky) {
        if (cviky.isNotEmpty()) {
            viewModel.startWorkout()
        }
    }

    if (workoutFinished) {
        LaunchedEffect(Unit) {
            navController.navigate("congrats/${viewModel.getTotalCalories()}") {
                popUpTo("classicWorkout") { inclusive = true }
            }
        }
    } else {
        currentCvik?.let { cvik ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF004d40))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = "Aktuálny cvik: ${cvik.nazov}",
                    style = MaterialTheme.typography.headlineSmall ,
                    color = Color.White
                )

                Text(
                    text = "Zostávajúci čas: ${remainingTime}s",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(
                        id = if (showPredImage) cvik.obrazokPred else cvik.obrazokPo
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                LinearProgressIndicator(
                    progress = if (cvik.trvanieAleboOpakovania > 0)
                        (cvik.trvanieAleboOpakovania - remainingTime).toFloat() / cvik.trvanieAleboOpakovania
                    else 1f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = { viewModel.resetWorkout() }) {
                    Text("Reštartovať tréning")
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Žiadne cviky k dispozícii.")
            }
        }
    }
}
